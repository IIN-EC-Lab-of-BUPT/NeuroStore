from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class DeviceBrowseQuery:
    """
    :description: 设备主题下的浏览查询
    """
    def __init__(self, address: str):
        self.requester = request(address)
    def deviceDetial(self,Did):
        """
        :description: 浏览指定设备数据所有内容
        :param Did: str，设备Did
        :return: ResultModel，设备数据所有内容
        """
        getStr = "/query/device/detail/{}".format(Did)
        r = self.requester.persistence_get(getStr)
        return r

