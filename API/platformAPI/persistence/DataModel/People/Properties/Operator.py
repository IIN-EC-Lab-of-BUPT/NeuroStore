"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Operator(Property):
    """
    :description: 操作者属性描述类
    """
    def __init__(self):
        super(Operator, self).__init__()
        self.content["CLASS"] = "OperatorP"

    def setJob(self,job: str):
        """
        :description: 设置职责
        :param job: str，职责
        :return: void
        """
        self.content["MajorTable"]["job"] = job

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description