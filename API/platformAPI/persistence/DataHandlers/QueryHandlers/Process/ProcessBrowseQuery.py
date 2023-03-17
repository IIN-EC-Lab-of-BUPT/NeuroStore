from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class ProcessBrowseQuery:
    """
    :description: 过程主题下的浏览查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def processDetail(self,processDid):
        """
        :description: 查询指定过程的所有信息
        :param processDid: str，过程的Did
        :return: ResultModel，指定过程的所有信息
        """
        getStr = "/query/process/detail/{}".format(processDid)
        r = self.requester.get(getStr)
        return r

    def processList(self):
        """
        :description: 查询所有的过程
        :return: ResultModel，所有的过程的信息
        """
        getStr = "/query/process/list"
        r = self.requester.get(getStr)
        return r

