"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from enum import Enum
class ProcessTypes(Enum):
    """
    :description: 过程类型枚举类
    """
    Competition = 1
    Experiment = 2
    Mission = 3
    Block = 4
    Trial = 5