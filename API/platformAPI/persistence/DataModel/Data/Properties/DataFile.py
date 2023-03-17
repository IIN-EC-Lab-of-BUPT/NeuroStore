"""
author:chen yang
contact author:1783680741@qq.com
date:2022/12/21
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class DataFile(Property):
    """
    :description: Block的结果
    """
    def __init__(self):
        super(DataFile, self).__init__()
        self.content["Class"] = "DataFiles"

    def setTime(self,time: int):
        """
        :description: 设置存储时时间
        :param time: int，设置存储时时间
        :return: void
        """
        self.content["Time"] = time

    def setStartTime(self,startTime: int):
        """
        :description: 设置数据开始时间
        :param startTime: interesting，开始时间
        :return: void
        """
        self.content["StartTime"] = startTime

    def setEndTime(self,endTime: int):
        """
        :description: 设置数据结束时间
        :param endTime: int,结束时间
        :return: void
        """
        self.content["EndTime"] = endTime

    def setFormat(self,format: str):
        """
        :description: 设置文件夹格式
        :param format: str,文件夹格式
        :return:
        """
        self.content["Format"] = format

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["Description"] = description

