from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class DataConditionalQuery:
    """
    :description: 数据主题下的条件查询
    """
    def __init__(self,address: str):
        self.requester = request(address)
    def dataForType(self,type):
        """
        :description: 查找指定类型的数据
        :param type: str，类型
        :return: ResultModel，指定类型的数据
        """
        getStr = "/query/data/type/{}".format(type)
        r = self.requester.persistence_get(getStr)
        return r

    def dataForFrequence(self,frequence):
        """
        :description: 查找指定频率的数据
        :param frequence: float，频率
        :return: ResultModel，指定频率的数据
        """
        getStr = "/query/data/frequence/{}".format(frequence)
        r = self.requester.persistence_get(getStr)
        return r

    def dataForChannel(self,channel):
        """
        :description: 查找含有指定信道的数据
        :param channel: str，信道
        :return: ResultModel，含有指定信道的数据
        """
        getStr = "/query/data/channel/{}".format(channel)
        r = self.requester.persistence_get(getStr)
        return r

    def dataForChannelNum(self,channelNum):
        """
        :description: 查找指定信道数的的数据
        :param channelNum: int，信道数
        :return: ResultModel，指定信道数的的数据
        """
        getStr = "/query/data/channelNum/{}".format(channelNum)
        r = self.requester.persistence_get(getStr)
        return r