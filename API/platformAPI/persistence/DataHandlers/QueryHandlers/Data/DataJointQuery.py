from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class DataJointQuery:
    """
    :description: 数据主题下的联合查询
    """
    def __init__(self, address: str):
        self.requester = request(address)

    def dataForProcess(self,processDid):
        """
        :description: 与指定过程的有关联的数据
        :param processDid: str，过程的Did
        :return: ResultModel，与指定过程的有关联的数据
        """
        getStr = "/query/data/process/{}".format(processDid)
        r = self.requester.persistence_get(getStr)
        return r
    def dataForPeople(self,PeopleDid):
        """
        :description: 与指定人员的有关联的数据
        :param PeopleDid: str，人员Did
        :return: ResultModel，与指定人员的有关联的数据
        """
        getStr = "/query/data/People/{}".format(PeopleDid)
        r = self.requester.persistence_get(getStr)
        return r
    def dataForDevice(self,Device):
        """
        :description: 与指定设备的有关联的数据
        :param Device: str，设备Did
        :return: ResultModel，与指定设备的有关联的数据
        """
        getStr = "/query/data/Device/{}".format(Device)
        r = self.requester.persistence_get(getStr)
        return r