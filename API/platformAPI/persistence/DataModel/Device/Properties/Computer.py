"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class Computer(Property):
    """
    :description: 计算机属性的描述类
    """
    def __init__(self):
        super(Computer, self).__init__()
        self.content["Class"] = "ComputerP"

    def setCPU(self,CPU: str):
        """
        :description: 设置CPU
        :param CPU: str，CPU
        :return: void
        """
        self.content["MajorTable"]["CPU"] = CPU

    def setIPAddress(self,IPAddress: str):
        """
        :description: 设置IP地址
        :param IPAddress: str，IP地址
        :return: void
        """
        self.content["MajorTable"]["IPAddress"] = IPAddress

    def setHostname(self,hostname: str):
        """
        :description: 设置主机名
        :param hostname: str，主机名
        :return: void
        """
        self.content["MajorTable"]["Hostname"] = hostname

    def setMemorySize(self,memorySize: int):
        """
        :description: 设置内存大小
        :param memorySize: int，内存大小
        :return: void
        """
        self.content["MajorTable"]["MemorySize"] = memorySize

    def setDiskSize(self,diskSize: int):
        """
        :description: 设置硬盘大小
        :param diskSize: int，硬盘大小
        :return: void
        """
        self.content["MajorTable"]["DiskSize"] = diskSize

    def setOS(self,OS: str):
        """
        :description: 设置操作系统
        :param OS: str，操作系统
        :return: void
        """
        self.content["MajorTable"]["OS"] = OS

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["OS"] = description