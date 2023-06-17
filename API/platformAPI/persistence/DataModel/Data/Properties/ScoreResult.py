"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class ScoreResult(Property):
    """
    :description: 成绩数据属性描述类
    """
    def __init__(self):
        super(ScoreResult, self).__init__()
        self.content["CLASS"] = "BlockResult"

    def setResult(self,time: int):
        """
        :description: 设置成绩
        :param time: int，成绩
        :return: void
        """
        self.content["result"] = time
