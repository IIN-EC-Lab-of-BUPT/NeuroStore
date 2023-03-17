"""
author:chen yang
contact author:1783680741@qq.com
date:2022/12/21
description:

"""
from enum import Enum
class DataTypes(Enum):
    """
    :description: Data的数据类型
    """
    PhysiologyData = 1
    EEGData = 2
    EyeTrackingData = 3
    MEGData = 4
    MRIData = 5
    EMGData = 6
    fNIRSData = 7
    MediaData = 8
    Picture = 9
    Sound = 10
    Video = 11
    ProblemsTable = 12
    BlockResult = 13
    TrialResult = 14
    AlgorithmCode = 15
    State = 16

