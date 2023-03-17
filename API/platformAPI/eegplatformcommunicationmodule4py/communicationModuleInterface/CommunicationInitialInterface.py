"""
@File:CommunicationInitialInterface.py
@Author:lcx
@Date:2020/10/714:37
@Desc:
"""
from abc import ABCMeta, abstractmethod

from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.componentInterface.QueryInterface import QueryInterface


class CommunicationInitialInterface(metaclass=ABCMeta):

    @staticmethod
    @abstractmethod
    def topic_query(communication_character: QueryInterface) -> list:
        """

        :param communication_character: a instance of interface "QueryInterface"
        :return: a list of all topic names, type: list<str>
        :throw: TopicQueryFailed, when failed "retry_limit" times
        """
        pass

    @staticmethod
    @abstractmethod
    def topic_create(topic: str, conf_path: str, num_partitions=1, replication_factor=1):
        """

        :param topic: the name of the topic that you want to create
        :param conf_path: path of configuration file, "bootstrap.servers" must be set
        :param num_partitions: the partition number of the topic that you want to create, default: 1
        :param replication_factor: the replication number of the topic that you want to create, default: 1
        :return: name of the topic which is created by this method, type: str
        :throw: TopicCreateFailed, when failed
                NoConfigFileException, when there is no config file in your config path
                WrongConfigContextException, when an invalid key is written in the configuration file
        """
        pass

    @staticmethod
    @abstractmethod
    def topic_delete(topic: str, conf_path: str):
        """

        :param topic: the name of the topic that you want to create. type: str
        :param conf_path: path of configuration file, "bootstrap.servers" must be set
        :return: name of the topic which is deleted by this method, type: str
        :throw: TopicDeleteFailed, when failed or there is no topic to delete
                NoConfigFileException, when there is no config file in your config path
                WrongConfigContextException, when an invalid key is written in the configuration file
        """
        pass
