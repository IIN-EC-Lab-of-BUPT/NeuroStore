"""
@File:CommunicationProducer.py
@Author:lcx
@Date:2020/10/714:34
@Desc:
"""
import json
import logging
import os
import time

from confluent_kafka import Producer
from confluent_kafka.cimpl import KafkaException

from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.componentInterface.QueryInterface import QueryInterface
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleInterface.CommunicationProducerInterface import CommunicationProducerInterface
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleInterface.communicationModuleException.Exceptions import *


class CommunicationProducer(CommunicationProducerInterface, QueryInterface):
    """
    :description: 生产者
    :param conf_path: the path of the producer config file that you want to use
    :throw: NoConfigFileException, when there is no config file in your config path
    """
    conf = dict()
    topic = ""
    producer = ""

    def __init__(self, conf_path: str):

        self.build_time = str(time.time())
        self.logger = logging.getLogger("communicationProducer-{}".format(self.build_time))
        self.debug_log_enable_flag = False
        self.retry_limit = None
        self.realtime_flush = None
        if not os.path.exists(conf_path):
            self.logger.fatal("no config file at {}".format(conf_path))
            raise NoConfigFileException("no config file at {}".format(conf_path))
        with open(conf_path, 'r') as load_f:
            self.conf = json.load(load_f)
        if "debug.log.enable" in self.conf.keys():
            self.logger.info("get configuration: debug.log.enable = {}".format(self.conf["debug.log.enable"]))
            self.debug_log_enable_flag = (self.conf["debug.log.enable"].lower() == "true")
            self.conf.pop("debug.log.enable")
        if "retry.limit" in self.conf.keys():
            self.retry_limit = int(self.conf["retry.limit"])
            self.conf.pop("retry.limit")
            self.logger.info("get configuration: retry.limit = {}".format(self.retry_limit))
        if "realtime.flush" in self.conf.keys():
            self.logger.info("get configuration: realtime.flush = {}".format(self.conf["realtime.flush"]))
            self.realtime_flush = (self.conf["realtime.flush"].lower() == "true")
            self.conf.pop("realtime.flush")
        self.producer = Producer(self.conf)
        self.logger.info("producer instance built: build_time={}, config={}, config at: {}".format(
            self.build_time, conf_path, str(self.conf)))

    def list_topics(self, topic: str = None, retry_limit: int = 1, timeout=0.5) -> list:
        result = None
        if self.retry_limit is not None:
            retry_limit = self.retry_limit
        else:
            retry_limit = retry_limit
        retry_time: int = 0
        while retry_time < retry_limit:
            try:
                result_cluster_metadata = self.producer.list_topics(topic, timeout)
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

    def send(self, topic: str, value: bytes, timeout: float = 1, key=None) -> None:
        """
        :description: 发送消息
        :param topic: the topic name that you want to send message to, type: str
        :param value: the message context, type: bytes
        :param timeout: the timeout for sending a msg, but that doesn't mean the msg sending is failed
        :param key: see "key" concept in kafka docs
        :throw: WrongMessageValueType, when "value" param isn't bytes-type
        """
        if not isinstance(value, bytes):
            raise WrongMessageValueType("want bytes but get {}".format(type(value)))
        else:
            self.producer.produce(topic, value, key)
            if self.realtime_flush:
                self.producer.flush(timeout=timeout)
            if self.debug_log_enable_flag:
                self.logger.debug("producer instance (build_time at {}) send a message at {}".format(self.build_time,
                                                                                                     time.time()))

    def close(self, timeout: float = 1) -> None:
        """
        :description: release resources
        :param: timeout: the flush timeout before closed
        """
        self.producer.flush(timeout=timeout)
        self.logger.info("producer instance closed: build_time={}".format(self.build_time))
