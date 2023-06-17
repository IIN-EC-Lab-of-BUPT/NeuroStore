from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class DeveiceConditionalQuery:
    """
    :description: 设备主题下的条件查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def deviceForType(self,type):
        """
        :description: 查询指定类型的设备数据
        :param type: str，类型
        :return: ResultModel，定类型的设备数据
        """
        getStr = "/query/device/type/{}".format(type)
        r = self.requester.persistence_get(getStr)
        return r

    # def deviceForElectrode(self,eletrodeName):
    #     """
    #     :description: 查找含有特定电极的设备
    #     :param eletrodeName: str，电极名字
    #     :return: ResultModel，含有特定电极的设备
    #     """
    #     getStr = "/query/device/eletrode/{}".format(eletrodeName)
    #     r = self.requester.persistence_get(getStr)
    #     return r
    #
    # def electrodeForDevice(self,deviceDid):
    #     """
    #     :description: 查找设备的所有电极
    #     :param deviceDid: str，设备的Did
    #     :return: ResultModel，设备的所有电极
    #     """
    #     getStr = "/query/device/electrodeList/{}".format(deviceDid)
    #     r = self.requester.persistence_get(getStr)
    #     return r
