"""
@File:CommunicationProducerInterface.py
@Author:lcx
@Date:2020/10/714:37
@Desc:
"""
from abc import ABCMeta, abstractmethod


class CommunicationProducerInterface(metaclass=ABCMeta):

    @abstractmethod
    def send(self, topic: str, value: bytes, timeout: float = 1, key=None) -> None:
        """

        :param topic: the topic name that you want to send message to, type: str
        :param value: the message context, type: bytes
        :param timeout: the timeout for sending a msg, but that doesn't mean the msg sending is failed
        :param key: see "key" concept in kafka docs
        :throw: WrongMessageValueType, when "value" param isn't bytes-type
        """
        pass

    @abstractmethod
    def close(self, timeout: float = 1) -> None:
        """

        release resources
        :param: timeout: the flush timeout before closed
        """
        pass
