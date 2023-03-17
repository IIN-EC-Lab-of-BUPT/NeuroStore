"""
author:chen yang
contact author:1783680741@qq.com
date:2022/12/21
description:

"""
from enum import Enum

class RelationType(Enum):
    """
    :description: 关系类型枚举类
    """
    FRIEND = 1
    PARENT = 2
    CONTAINT = 3
    CREATE = 4
    IS = 5