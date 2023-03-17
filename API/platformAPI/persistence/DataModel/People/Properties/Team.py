"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Team(Property):
    """
    :description: 团队属性描述类
    """
    def __init__(self):
        super(Team, self).__init__()
        self.content["Class"] = "TeamP"

    def setTeamName(self,teamName: str):
        """
        :description: 设置团队名字
        :param teamName: str，团队名字
        :return: void
        """
        self.content["MajorTable"]["TeamName"] = teamName

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description