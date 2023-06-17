"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.People.PeopleTypes import PeopleTypes
from platformAPI.persistence.DataModel.DataModel import DataModelInterface

class People(DataModelInterface):
    """
    :description: 人员主题类
    """
    def __init__(self):
        super(People,self).__init__()
        self.content["CLASS"] = "People"

    def resetDid(self,did: str):
        """
        :description: 重设Did，若不重设，则自动生成
        :param did: str，did
        :return: void
        """
        self.content["MajorTable"]["Did"] = did

    def setName(self,name: str):
        """
        :description: 设置名字
        :param name: str，name
        :return: void
        """
        self.content["MajorTable"]["Name"] = name

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description

    def setDataType(self,peopleTypes: str):
        """
        :description: 设置数据类型
        :param peopleTypes: str，数据类型
        :return: void
        """
        self.content["MajorTable"]["Type"] = peopleTypes