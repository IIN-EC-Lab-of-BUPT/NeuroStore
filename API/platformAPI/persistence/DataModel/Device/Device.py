"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Device.DeviceTypes import DeviceTypes
from platformAPI.persistence.DataModel.DataModel import DataModelInterface

class Device(DataModelInterface):
    """
    :description: 设备主题类
    """
    def __init__(self):
        super(Device,self).__init__()
        self.content["Class"] = "Devices"


    def resetDid(self,did: str):
        """
        :description: 重设Did，如不重设，则自动生成
        :param did: str，Did
        :return: void
        """
        self.content["MajorTable"]["Did"] = did

    def setManufacture(self,manufacture: str):
        """
        :description: 设置制造商
        :param manufacture: str，制造商
        :return: void
        """
        self.content["MajorTable"]["manufacture"] = manufacture

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description

    def setDataType(self,devicetype: DeviceTypes):
        """
        :description: 设置设备类型
        :param devicetype: DeviceTypes,设备类型
        :return: void
        """
        self.content["Type"] = devicetype.value