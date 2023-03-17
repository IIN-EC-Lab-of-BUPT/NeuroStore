"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class User(Property):
    """
    :description: 使用者属性描述类
    """
    def __init__(self):
        super(User, self).__init__()
        self.content["Class"] = "UserP"

    def setAccount(self,account: str):
        """
        :description: 设置账户
        :param account: str，账户
        :return: void
        """

        self.content["MajorTable"]["Account"] = account

    def setPassword(self,password: str):
        """
        :description: 设置密码
        :param password: 密码
        :return: void
        """
        self.content["MajorTable"]["Password"] = password

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description