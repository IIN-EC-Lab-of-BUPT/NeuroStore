"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Maintainer(Property):
    """
    :description: 维护人员属性描述类
    """
    def __init__(self):
        super(Maintainer, self).__init__()
        self.content["Class"] = "MaintainerP"

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
        :param password: str，密码
        :return: void
        """
        self.content["MajorTable"]["Password"] = password

    def setResponsibility(self,responsibility: str):
        """
        :description: 设置职责
        :param responsibility: str，职责
        :return: void
        """
        self.content["MajorTable"]["Responsibility"] = responsibility

    def setPermissionLevel(self,permissionLevel: str):
        """
        :description: 设置权限等级
        :param permissionLevel: str，权限等级
        :return: void
        """
        self.content["MajorTable"]["PermissionLevel"] = permissionLevel

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description