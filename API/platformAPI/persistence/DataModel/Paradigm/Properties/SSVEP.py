"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class SSVEP(Property):
    """
    :description: SSVEP范式属性描述类
    """
    def __init__(self):
        super(SSVEP, self).__init__()
        self.content["Class"] = "SSVEPP"

    def setFrenquence(self,frenquence: int):
        """
        :description: 设置频率
        :param frenquence: int，频率
        :return: void
        """
        self.content["MajorTable"]["Frenquence"] = frenquence

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description