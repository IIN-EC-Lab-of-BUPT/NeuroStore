from abc import ABCMeta, abstractmethod


class QueryInterface(metaclass=ABCMeta):

    @staticmethod
    @abstractmethod
    def list_topics(topic: str = None, retry_limit: int = 1, timeout: float = 0.5) -> list:
        """

        :param topic: the topic name that you want to inquire
        :param retry_limit: time of retries after query failure
        :param timeout: query wait time, queries beyond this time will be defined as failure
        :return: a list of all topic names, type: list<str>
        :throw: TopicQueryFailed, when failed "retry_limit" times
        """
        pass
