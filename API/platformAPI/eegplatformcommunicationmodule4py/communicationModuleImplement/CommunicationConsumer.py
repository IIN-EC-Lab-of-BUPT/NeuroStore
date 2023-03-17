"""
@File:CommunicationConsumer.py
@Author:lcx
@Date:2020/10/714:34
@Desc:
"""
import logging
import os
import time

from confluent_kafka.cimpl import KafkaException

from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.componentInterface.QueryInterface import QueryInterface

from platformAPI.eegplatformcommunicationmodule4py.communicationModuleInterface.CommunicationConsumerInterface import CommunicationConsumerInterface
from confluent_kafka import Consumer
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleInterface.communicationModuleException.Exceptions import *
import json


class CommunicationConsumer(CommunicationConsumerInterface, QueryInterface):
    """
    :description: 消费者
    :param conf_path: the path of the producer config file that you want to use
    :param consumer_id: should be unique in the system, UUID suggested
    :throw: NoConfigFileException, when there is no config file in your config path
    """
    conf = dict()
    topic = ""
    consumer = ""
    def __init__(self, conf_path: str, consumer_id: str):

        self.logger = logging.getLogger("CommunicationConsumer-{}".format(consumer_id))
        self.logger.info("start initializing consumer")
        self.debug_log_enable_flag = False
        self.retry_limit = None
        self.consumer_id = None
        if not os.path.exists(conf_path):
            self.logger.fatal("no config file at {}".format(conf_path))
            raise NoConfigFileException("no config file at {}".format(conf_path))
        with open(conf_path, 'r') as load_f:
            self.conf = json.load(load_f)
        if "debug.log.enable" in self.conf.keys():
            self.logger.info("get configuration: debug.log.enable = {}".format(self.conf["debug.log.enable"]))
            self.debug_log_enable_flag = (self.conf["debug.log.enable"].lower() == "true")
            self.conf.pop("debug.log.enable")
        self.topic: str = None
        if "retry.limit" in self.conf.keys():
            self.retry_limit = int(self.conf["retry.limit"])
            self.conf.pop("retry.limit")
            self.logger.info("get configuration: retry.limit = {}".format(self.retry_limit))
        self.conf["group.id"] = consumer_id
        self.consumer_id = consumer_id
        self.consumer = Consumer(self.conf)
        self.logger.info("consumer instance built: id={}, config={}, config at: {}".format(consumer_id,
                                                                                           str(self.conf), conf_path))

    def subscribe(self, topic: str) -> None:
        """
        :description: 订阅
        :param topic: the name of the topic this consumer need to subscribe
        :throw: TopicNotAvailableException, when subscribing a topic that doesn't exist
        """
        if topic not in self.list_topics():
            self.logger.fatal("no topic named {}".format(topic))
            raise TopicNotAvailableException("no topic named {}".format(topic))
        self.consumer.subscribe([topic])
        self.topic = topic
        self.logger.info("subscribed topic: {}".format(self.topic))

    def unsubscribe(self) -> None:
        """
        :description: 取消订阅
        """
        self.consumer.unsubscribe()
        topic = self.topic
        self.topic = None
        self.logger.info("unsubscribed topic: {}".format(topic))

    def list_topics(self, topic=None, retry_limit=1, timeout=0.5) -> list:
        result = None
        if self.retry_limit is not None:
            retry_limit = self.retry_limit
        else:
            retry_limit = retry_limit
        retry_time: int = 0
        while retry_time < retry_limit:
            try:
                result_cluster_metadata = self.consumer.list_topics(topic, timeout)
            except KafkaException as ke:
                retry_time += 1
                self.logger.error("get topic list failed, retry for {} time".format(str(retry_time)))
                time.sleep(1)
            else:
                result = list(result_cluster_metadata.topics.keys())
                self.logger.info("get topic list: {}".format(str(result)))
                break
        if result is None:
            self.logger.fatal("topic list query failed, retried {} times, waited for {} secs, found broker not "
                              "available, please check the connection.".format(str(retry_limit),
                                                                               str(retry_limit * 1.5)))
            raise TopicQueryFailed("topic list query failed, retried {} times, waited for {} secs, found broker not "
                                   "available, please check the connection.".format(str(retry_limit),
                                                                                    str(retry_limit * 1.5)))
        return result

    def receive(self) -> bytes:
        """
        :description: 接收消息
        :return: message received within 0.1s. type: bytes (when the message is received) or None (when there is no message in 0.1s)
        :throw: NoSubscribeException, when calling this method before subscribe a topic
        """
        if self.topic is None:
            raise NoSubscribeException("please subscribe a topic before calling this method")
        msg = self.consumer.poll(timeout=0.1)
        if msg:
            if self.debug_log_enable_flag:
                self.logger.debug("received a message")
            return msg.value()
        else:
            return None

    def timestamp_receive(self) -> list:
        """
        :description: 接收带有时间戳的消息
        :return: a list of timestamp and message value.
        """
        if self.topic is None:
            raise NoSubscribeException("please subscribe a topic before calling this method")
        msg = self.consumer.poll(timeout=0.1)
        if msg:
            if self.debug_log_enable_flag:
                self.logger.debug("received a timestamp message")
            return [msg.timestamp(), msg.value()]
        else:
            return None

    def close(self):
        """
        :description: 关闭
        """
        self.unsubscribe()
        self.consumer.close()
        self.logger.info("consumer instance closed: id={}".format(self.consumer_id))
