from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class ParadigmJointQuery:
    """
    :description: 范式主题下的联合查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def ParadigmForProcess(self,processDid):
        """
        :description: 查找与指定过程有关的范式数据
        :param processDid: str，过程的Did
        :return: ResultModel，与指定过程有关的范式数据
        """
        getStr = "/query/paradigm/process/{}".format(processDid)
        r = self.requester.persistence_get(getStr)
        return r
