"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Experimenter(Property):
    """
    :description: 实验人员属性描述类
    """
    def __init__(self):
        super(Experimenter, self).__init__()
        self.content["CLASS"] = "ExperimenterP"

    def setExperimentName(self, experimentName: str):
        """
        :description: 设置实验名字
        :param experimentName: str，实验名字
        :return: void
        """
        self.content["MajorTable"]["ExperimentName"] = experimentName
