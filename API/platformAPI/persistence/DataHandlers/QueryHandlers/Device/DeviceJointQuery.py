from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class DeviceJointQuery:
    """
    :description: 设备主题下的联合查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def deviceForData(self,dataDid):
        """
        :description: 查询与指定数据有关的设备
        :param dataDid: str，数据的Did
        :return: ResultModel，与指定数据有关的设备的数据
        """
        getStr = "/query/device/data/{}".format(dataDid)
        r = self.requester.persistence_get(getStr)
        return r
    def deviceForProcess(self,processDid):
        """
        :description: 查询与指定过程有关的设备
        :param processDid: str，过程的Did
        :return: ResultModel，与指定过程有关的设备的数据
        """
        getStr = "/query/device/process/{}".format(processDid)
        r = self.requester.persistence_get(getStr)
        return r
