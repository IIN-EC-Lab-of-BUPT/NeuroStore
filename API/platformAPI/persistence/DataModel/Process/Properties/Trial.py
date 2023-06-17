"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Trial(Property):
    """
    :description: Trial属性描述类
    """
    def __init__(self):
        super(Trial, self).__init__()
        self.content["CLASS"] = "TrialP"

    def setResult(self,result: str):
        """
        :description: 设置结果
        :param result: str，结果
        :return: void
        """
        self.content["MajorTable"]["Result"] = result