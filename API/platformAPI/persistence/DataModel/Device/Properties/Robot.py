"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Robot(Property):
    """
    :description: 机器人属性的描述类
    """
    def __init__(self):
        super(Robot, self).__init__()
        self.content["Class"] = "RobotP"

    def setFunction(self,function: str):
        """
        :description: 设置功能
        :param function: str，功能
        :return: void
        """
        self.content["MajorTable"]["Function"] = function

    def setIsIntelligent(self,isIntelligent: bool):
        """
        :description: 设置是否智能
        :param isIntelligent: bool，是否智能
        :return: void
        """
        self.content["MajorTable"]["IsIntelligent"] = isIntelligent

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["OS"] = description