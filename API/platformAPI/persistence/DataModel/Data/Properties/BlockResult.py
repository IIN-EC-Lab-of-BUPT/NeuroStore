"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class BlockResult(Property):
    """
    :description: Block的结果数据属性描述类
    """
    def __init__(self):
        super(BlockResult, self).__init__()
        self.content["Class"] = "BlockResult"

    def setResult(self,time: int):
        """
        :description: 设置result属性
        :param time: 传入参数
        :return: void
        """
        self.content["result"] = time
