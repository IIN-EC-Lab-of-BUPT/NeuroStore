"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class TrialResult(Property):
    """
    :description: Trial的结果数据属性描述类
    """
    def __init__(self):
        super(TrialResult, self).__init__()
        self.content["CLASS"] = "TrialResult"

    def setResultNum(self,resultNum: str):
        """
        :description: 设置结果
        :param resultNum: str,结果
        :return: void
        """
        self.content["resultNum"] = resultNum

    def setUsedDataLength(self,usedDataLength: int):
        """
        :description: 设置结果
        :param usedDataLength:
        :return: void
        """
        self.content["usedDataLength"] = usedDataLength

    def setUsedTime(self,usedTime: float):
        """
        :description: 设置用时
        :param usedTime: float，用时
        :return: void
        """
        self.content["usedTime"] = usedTime

    def setScore(self,score: float):
        """
        :description: 设置分数
        :param score: float，分数
        :return: void
        """
        self.content["score"] = score

    def setResult(self,result: str):
        """
        :description: 设置结果
        :param result: str，结果
        :return: void
        """
        self.content["Result"] = result

