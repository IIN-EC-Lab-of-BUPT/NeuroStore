"""
author:CY
contact author:1783680741@qq.com
date:2023/2/14
description:

"""
class Event:
    """
    :description: 事件属性描述类
    """
    def __init__(self):
        self.content = {}

    def setTime(self,time: int):
        """
        :description: 设置时间
        :param time: int，时间
        :return: void
        """
        self.content["Time"] = time

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["Description"] = description
