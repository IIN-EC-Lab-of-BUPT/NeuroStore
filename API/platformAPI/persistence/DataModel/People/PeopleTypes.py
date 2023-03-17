"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from enum import Enum
class PeopleTypes(Enum):
    """
    :description: 人员类型枚举类
    """
    User = 1
    Developer = 2
    Maintainer = 3
    Subject = 4
    Experiment = 5
    Operator = 6
    Participant = 7
    Team = 8


