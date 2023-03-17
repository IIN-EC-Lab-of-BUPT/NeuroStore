"""
@File:CommunicationInitial.py
@Author:lcx
@Date:2020/10/714:38
@Desc:
"""
import json
import os

from confluent_kafka.admin import AdminClient, NewTopic
from confluent_kafka.cimpl import KafkaException, KafkaError

from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.componentInterface.QueryInterface import QueryInterface
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleInterface.CommunicationInitialInterface import CommunicationInitialInterface
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleInterface.communicationModuleException import Exceptions


class CommunicationInitial(CommunicationInitialInterface):

    @staticmethod
    def topic_query(communication_character: QueryInterface) -> list:
        """
        :description: 查询topic列表
        :param communication_character: a instance of interface "QueryInterface"
        :return: a list of all topic names, type: list<str>
        :throw: TopicQueryFailed, when failed "retry_limit" times
        """
        return communication_character.list_topics()

    @staticmethod
    def topic_create(topic: str, conf_path: str, num_partitions=1, replication_factor=1):
        """
        :description: 创建topic
        :param topic: the name of the topic that you want to create
        :param conf_path: path of configuration file, "bootstrap.servers" must be set
        :param num_partitions: the partition number of the topic that you want to create, default: 1
        :param replication_factor: the replication number of the topic that you want to create, default: 1
        :return: name of the topic which is created by this method, type: str
        :throw: TopicCreateFailed, when failed，NoConfigFileException, when there is no config file in your config path，WrongConfigContextException, when an invalid key is written in the configuration file
        """
        retry_limit = 0
        if not os.path.exists(conf_path):
            raise Exceptions.NoConfigFileException("NoConfigFileException in {}".format(conf_path))
        with open(conf_path, 'r') as load_f:
            conf = json.load(load_f)
        if "bootstrap.servers" not in conf.keys():
            raise Exceptions.WrongConfigContextException("need bootstrap.servers")
        if "retry.limit" in conf.keys():
            retry_limit = int(conf["retry.limit"])
            conf.pop("retry.limit")

        retry_time = -1
        exception = None
        while retry_time < retry_limit:
            admin_client = AdminClient(conf)
            new_topics = [NewTopic(topic, num_partitions, replication_factor)]
            fs = admin_client.create_topics(new_topics)
            for topic, f in fs.items():
                try:
                    f.result(timeout=1)  # The result itself is None
                    return topic
                except KafkaException as ke:
                    # topic already exits.
                    if ke.args[0].code() == KafkaError.TOPIC_ALREADY_EXISTS:
                        return topic
                except Exception as e:
                    retry_time += 1
                    exception = e
        if exception:
            raise Exceptions.TopicCreateFailed(exception)

    @staticmethod
    def topic_delete(topic: str, conf_path: str):
        """
        :description: 删除topic
        :param topic: the name of the topic that you want to create. type: str
        :param conf_path: path of configuration file, "bootstrap.servers" must be set
        :return: name of the topic which is deleted by this method, type: str
        :throw: TopicDeleteFailed, when failed or there is no topic to delete，NoConfigFileException, when there is no config file in your config path，WrongConfigContextException, when an invalid key is written in the configuration file
        """
        if not os.path.exists(conf_path):
            raise Exceptions.NoConfigFileException
        with open(conf_path, 'r') as load_f:
            conf = json.load(load_f)
        if not "bootstrap.servers" in conf.keys():
            raise Exceptions.WrongConfigContextException
        if "retry.limit" in conf.keys():
            conf.pop("retry.limit")
        admin_client = AdminClient(conf)
        fs = admin_client.delete_topics([topic], request_timeout=1)
        for topic, f in fs.items():
            try:
                f.result()  # The result itself is None
                return topic
            except Exception as e:
                raise Exceptions.TopicDeleteFailed(e)
