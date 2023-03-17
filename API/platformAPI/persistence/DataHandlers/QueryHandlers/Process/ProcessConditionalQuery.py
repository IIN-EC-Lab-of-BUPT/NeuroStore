from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class ProcessConditionalQuery:
    """
    :description: 过程主题下的条件查询
    """
    def __init__(self,address: str):
        self.requester = request(address)


    def descriptionForProcess(self,content):
        """
        :description:
        :param content:
        :return:
        """
        getStr = "/query/process/description/{}".format(content)
        r = self.requester.get(getStr)
        return r

    # def processForResult(self,processDid):
    #     getStr = "/query/process/result/{}".format(processDid)
    #     r = self.requester.get(getStr)
    #     return r

    # def processForStartTime(self):
    #     getStr = "/query/process/result/{}".format(processDid)
    #     r = self.requester.get(getStr)
    #     return r

    def processForDescriptionLike(self,content):
        """
        :description: 描述中含有指定字段的过程
        :param content:str，指定字段
        :return: ResultModel，有指定字段的过程信息
        """
        getStr = "/query/process/description/{}".format(content)
        r = self.requester.get(getStr)
        return r
