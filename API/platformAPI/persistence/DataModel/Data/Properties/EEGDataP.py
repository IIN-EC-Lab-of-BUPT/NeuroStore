"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class EEGDataP(Property):
    """
    :description: EEG数据属性描述类
    """
    def __init__(self):
        super(EEGDataP, self).__init__()
        self.content["CLASS"] = "TrialResult"

    def setSampleFrequency(self,sampleFrequency: int):
        """
        :description: 设置采样率
        :param sampleFrequency: int，采样率
        :return: void
        """
        self.content["SampleFrequency"] = sampleFrequency

    def setRowNum(self,rowNum: int):
        """
        :description: 设置导联数
        :param rowNum: int，导联数
        :return: void
        """
        self.content["RowNum"] = rowNum

    def setClumnNum(self,clumnNum: int):
        """
        :description: 设置数据长度
        :param clumnNum: int，数据长度
        :return: void
        """
        self.content["ClumnNum"] = clumnNum