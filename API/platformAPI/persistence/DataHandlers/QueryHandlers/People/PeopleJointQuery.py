from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class PeopleJointQuery:
    """
    :description: 人员主题下的联合查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def PeopleForProcess(self,processDid):
        """
        :description: 查找与指定过程有关联的人员
        :param processDid: str，过程的Did
        :return: ResultModel，与指定过程有关联的人员信息
        """
        getStr = "/query/process/{}".format(processDid)
        r = self.requester.get(getStr)
        return r

    def PeopleForData(self,dataDid):
        """
        :description: 查找与指定数据有关联的人员
        :param dataDid: str，数据的Did
        :return: ResultModel，与指定数据有关联的人员信息
        """
        getStr = "/query/data/{}".format(dataDid)
        r = self.requester.get(getStr)
        return r
