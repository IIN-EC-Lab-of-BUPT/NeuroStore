"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Block(Property):
    """
    :description: Block属性描述类
    """
    def __init__(self):
        super(Block, self).__init__()
        self.content["Class"] = "BlockP"

    def setStimulateOrder(self,stimulateOrder: str):
        """
        :description: 设置刺激顺序
        :param stimulateOrder: str，刺激顺序
        :return: void
        """

        self.content["MajorTable"]["stimulateOrder"] = stimulateOrder

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description