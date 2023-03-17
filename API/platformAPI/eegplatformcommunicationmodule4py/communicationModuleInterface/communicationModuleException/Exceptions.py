"""
@File:Exceptions.py
@Author:lcx
@Date:2020/10/915:29
@Desc:
"""


class CommunicationException(Exception):
    """Base class of communication exceptions"""

    def __init__(self, message):
        super().__init__(self)
        self.superior = None
        if isinstance(message, Exception):
            self.superior = message.__class__.__name__
            self.exception_info = str(message)
        else:
            self.exception_info = message

    def __str__(self):
        if self.superior:
            return "wrapped " + self.superior + ": " + self.exception_info
        return self.exception_info


class TopicNotAvailableException(CommunicationException):
    """Raise when subscribing a topic which is not available"""
    pass


class NoConfigFileException(CommunicationException):
    """Raise when there is no such configure file in the param path"""
    pass


class WrongConfigContextException(CommunicationException):
    """Raise when an invalid key is written in the configuration file"""
    pass


class TopicQueryFailed(CommunicationException):
    """Raise when calling CommunicationInitial.topicQuery method failed"""
    pass


class TopicCreateFailed(CommunicationException):
    """Raise when calling CommunicationInitial.topicCreate method failed"""
    pass


class TopicDeleteFailed(CommunicationException):
    """Raise when calling CommunicationInitial.topicDelete method failed"""
    pass


class WrongMessageValueType(CommunicationException):
    """Raise when calling CommunicationProducer.send(...) with
        a value param of no-bytes type
    """
    pass


class NoSubscribeException(CommunicationException):
    """Raise when receive before subscribe"""
    pass
