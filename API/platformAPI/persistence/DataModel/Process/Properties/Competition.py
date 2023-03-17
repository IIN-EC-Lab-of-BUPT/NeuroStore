"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Competition(Property):
    """
    :description: 比赛属性描述类
    """
    def __init__(self):
        super(Competition, self).__init__()
        self.content["Class"] = "CompetitionP"

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description