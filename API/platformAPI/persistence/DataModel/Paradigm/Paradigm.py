"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Paradigm.ParadigmTypes import ParadigmTypes
from platformAPI.persistence.DataModel.DataModel import DataModelInterface

class Paradigm(DataModelInterface):
    """
    :description: 范式主题类
    """
    def __init__(self):
        super(Paradigm,self).__init__()
        self.content["CLASS"] = "Paradigms"

    def resetDid(self,did: str):
        """
        :description: 重设Did，若不重设则自动生成
        :param did: str，Did
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

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description

    def setDataType(self,paradigmTypes: ParadigmTypes):
        """
        :description: 设置范式类型
        :param paradigmTypes: ParadigmTypes，范式类型
        :return: void
        """
        self.content["Type"] = paradigmTypes.value