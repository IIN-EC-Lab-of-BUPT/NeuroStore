"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Sensor(Property):
    """
    :description: 传感器属性的描述类
    """
    def __init__(self):
        super(Sensor, self).__init__()
        self.content["CLASS"] = "SensorP"

    def setFunction(self,function: str):
        """
        :description: 设置功能
        :param function: str，功能
        :return: void
        """
        self.content["MajorTable"]["Function"] = function

    def setSignalSouce(self,signalSouce: str):
        """
        :description: 设置信号源
        :param signalSouce: str，信号源
        :return: void
        """
        self.content["MajorTable"]["SignalSouce"] = signalSouce

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["OS"] = description