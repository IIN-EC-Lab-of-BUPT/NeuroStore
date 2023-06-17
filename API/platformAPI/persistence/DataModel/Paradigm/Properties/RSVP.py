"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class RSVP(Property):
    """
    :description: RSVP范式属性描述类
    """
    def __init__(self):
        super(RSVP, self).__init__()
        self.content["CLASS"] = "RSVPP"

    def setTarget(self,target: str):
        """
        :description: 设置目标
        :param target: str，目标
        :return: void
        """
        self.content["MajorTable"]["Target"] = target

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description