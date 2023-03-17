"""
author:chen yang
contact author:1783680741@qq.com
date:2022/12/21
description:

"""
from platformAPI.persistence.DataModel.Data.DataTypes import DataTypes
from platformAPI.persistence.DataModel.DataModel import DataModelInterface
import base64

class Data(DataModelInterface):
    """
    :description: Data主题中的主类
    """
    def __init__(self):
        super(Data,self).__init__()
        self.content["Class"] = "Data"


    def resetDid(self,did: str):
        """
        :description: 重设Did，若不调用，会自动生成Did
        :param did: str，唯一标识
        :return: void
        """
        self.content["MajorTable"]["Did"] = did

    def setName(self,name: str):
        """
        :description: 设置名字
        :param name: str，名字
        :return: void
        """
        self.content["MajorTable"]["Name"] = name

    def addData(self,data: bytes):
        """
        :description: 添加数据
        :param data: bytes，数据
        :return: void
        """
        content = base64.b64encode(data,'utf-8')
        self.content["CONTENT"] = self.content["CONTENT"] + content

    def setDataType(self,datatype: str):
        """
        :description: 设置数据类型
        :param datatype: str，数据类型
        :return: void
        """
        self.content["MajorTable"]["Type"] = datatype