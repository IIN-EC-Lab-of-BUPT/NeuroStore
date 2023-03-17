"""
@File:CommunicationConsumerInterface.py
@Author:lcx
@Date:2020/10/714:36
@Desc:
"""
from abc import ABCMeta, abstractmethod


class CommunicationConsumerInterface(metaclass=ABCMeta):

    @abstractmethod
    def subscribe(self, topic: str) -> None:
        """

        :param topic: the name of the topic this consumer need to subscribe
        :throw: TopicNotAvailableException, when subscribing a topic that doesn't exist
        """
        pass

    @abstractmethod
    def unsubscribe(self) -> None:
        pass

    @abstractmethod
    def receive(self) -> bytes:
        """

        :return: message received within 0.1s. type: bytes (when the message is received) or None (when there is no message in 0.1s)
        :throw: NoSubscribeException, when calling this method before subscribe a topic
        """
        pass

    @abstractmethod
    def timestamp_receive(self) -> list:
        """

        :return: a list of timestamp and message value.
        the first element in the list is a tulpe of timestamp. the first element is timestamp type,
        which is a number in 0, 1 or 2.
        0 means the timestamp isn't available, in this case, the return timestamp should be ignore.
        1 means the return timestamp is the number of milliseconds of the message creation time.
        2 means the return timestamp is the number of milliseconds of the broker receive time.
        the first element in the list is bytes of  message value.
        type: [(int, int), bytes] or None(when there is no message in timeout)
        :throw: NoSubscribeException, when calling this method before subscribe a topic
        """
        pass

    @abstractmethod
    def close(self) -> None:
        """

        release resources
        """
        pass
