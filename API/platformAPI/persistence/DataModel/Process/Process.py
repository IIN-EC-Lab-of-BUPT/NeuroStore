"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Process.ProcessTypes import ProcessTypes
from platformAPI.persistence.DataModel.DataModel import DataModelInterface
from platformAPI.persistence.DataModel.Process.Mount.Event import Event
class Process(DataModelInterface):
    """
    :description: 过程主题类
    """
    def __init__(self):
        super(Process,self).__init__()
        self.content["CLASS"] = "Process"

    def resetDid(self,did: str):
        """
        :description: 重设Did，若不重设，则自动生成
        :param did: str，did
        :return: void
        """
        self.content["MajorTable"]["Did"] = did

    def setName(self,name: str):
        """
        :description: 设置名字
        :param name: str，名字
        :return: void
        """
        self.content["MajorTable"]["Name"] = name

    def setStartTime(self,startTime: int):
        """
        :description: 设置开始时间
        :param startTime: int，startTime
        :return: void
        """
        self.content["MajorTable"]["StartTime"] = startTime

    def setEndTime(self, endTime: int):
        """
        :description: 设置结束时间
        :param endTime: int， 结束时间
        :return: void
        """
        self.content["MajorTable"]["EndTime"] = endTime

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description

    def addEvent(self,event:Event):
        pass

    def setDataType(self,processTypes: ProcessTypes):
        """
        :description: 设置过程类型
        :param processTypes: ProcessTypes，过程类型
        :return: void
        """
        self.content["Type"] = processTypes.value