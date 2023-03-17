from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class ProcessJointQuey:
    """
    :description: 过程主题下的联合查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def fatherProcessForProcess(self,processDid):
        """
        :description: 指定的过程的父过程
        :param processDid: str，过程的Did
        :return: ResultModel，指定的过程的父过程信息
        """
        getStr = "/query/process/fatherProcess/{}".format(processDid)
        r = self.requester.get(getStr)
        return r


    def sonsProcessForProcess(self,processDid):
        """
        :description: 指定过程的所有子过程
        :param processDid: str，过程的Did
        :return: ResultModel，指定过程的所有子过程信息
        """
        getStr = "/query/process/sonsProcess/{}".format(processDid)
        r = self.requester.get(getStr)
        return r

    def processForData(self,dataDid):
        """
        :description: 指定与指定数据有关联的过程
        :param dataDid: str，数据的Did
        :return: ResultModel，指定与指定数据有关联的过程信息
        """
        getStr = "/query/process/data/{}".format(dataDid)
        r = self.requester.get(getStr)
        return r

    def processForPeople(self,peopleDid):
        """
        :description: 与指定人员有关联的过程
        :param peopleDid: str，人员的Did
        :return: ResultModel，与指定人员有关联的过程信息
        """
        getStr = "/query/process/people/{}".format(peopleDid)
        r = self.requester.get(getStr)
        return r

    def processForParadigm(self,paradigmDid):
        """
        :description: 与指定范式有关联的过程
        :param paradigmDid: str，范式的Did
        :return: ResultModel，与指定范式有关联的过程信息
        """
        getStr = "/query/process/paradigm/{}".format(paradigmDid)
        r = self.requester.get(getStr)
        return r

    def processForDevice(self,deviceDid):
        """
        :description: 与指定设备有关联的过程
        :param deviceDid: str，设备Did
        :return: ResultModel，与指定设备有关联的过程信息
        """
        getStr = "/query/process/device/{}".format(deviceDid)
        r = self.requester.get(getStr)
        return r
