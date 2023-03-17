"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property
from datetime import datetime


class Subject(Property):
    """
    :description: 被试者属性描述类
    """
    def __init__(self):
        super(Subject, self).__init__()
        self.content["Class"] = "SubjectP"

    def setName(self,name: str):
        """
        :description: 设置名字
        :param name: str，名字
        :return: void
        """
        self.content["MajorTable"]["Name"] = name

    def setGender(self,gender: bool):
        """
        :description: 设置性别
        :param gender: bool，性别
        :return: void
        """
        self.content["MajorTable"]["Gender"] = gender

    def setBirthday(self,birthday: datetime):
        """
        :description: 设置生日
        :param birthday: datetime，生日
        :return: void
        """
        self.content["MajorTable"]["Birthday"] = birthday.strptime('%Y/%m/%d')

    def setID(self,ID: int):
        """
        :description: 设置ID
        :param ID: int，ID
        :return: void
        """
        self.content["MajorTable"]["ID"] = ID

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description