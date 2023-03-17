"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class MI(Property):
    """
    :description: MI范式属性描述类
    """
    def __init__(self):
        super(MI, self).__init__()
        self.content["Class"] = "MIP"

    def setBodyPart(self,bodyPart: str):
        """
        :description: 设置身体部位
        :param bodyPart: str，身体部位
        :return: void
        """
        self.content["MajorTable"]["BodyPart"] = bodyPart

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description