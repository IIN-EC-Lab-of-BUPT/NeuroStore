"""
author:chen yang
contact author:1783680741@qq.com
date:2022/12/21
description:

"""
from abc import ABCMeta, abstractmethod

from platformAPI.persistence.DataModel.DataClass import DataClass
from platformAPI.persistence.DataModel.Property import Property
from platformAPI.persistence.DataModel.RelationType import RelationType
import json
import uuid

class DataModelInterface(object):
    pass


class DataModelInterface():
    """
    :description: 数据模型接口
    """
    def __init__(self):
        self.content = {
            "MajorTable":{"Did":uuid.uuid1()},
            "Properties":[],
            "Relation":{},
            "Action":"0"
                        }

    def toBytes(self):
        """
        :description: 序列化
        :return: void
        """
        if("Did" not in self.content["MajorTable"]):
             raise Exception("No primary key!")
        mes_conversed = json.dumps(self.content, sort_keys=True, indent=4,
                                   separators=(',', ': '))
        return bytes(mes_conversed, encoding="utf-8")

    def addProperty(self,property: Property):
        """
        :description: 添加属性
        :param property: Property,属性
        :return: void
        """
        self.content["Properties"].append(property.getContent())

    def inRelationTo(self,dataModel: DataModelInterface):
        """
        :description: 用模型添加关系
        :param dataModel: DaaModelInterface,数据模型
        :return: void
        """
        relation = {
            "Class":dataModel.content["Class"],
            "Did":dataModel.content["MajorTable"]["Did"],
            "Type":1
        }
        self.content["Relation"].append(relation)


    def inRelationTo(self,dataModel: DataModelInterface,relationType: RelationType):
        """
        :description: 用唯一标识添加关系
        :param dataModel: DataModelInterface，数据模型
        :param relationType: RelationType，关系种类
        :return: void
        """
        relation = {
            "Class": dataModel.content["Class"],
            "Did": dataModel.content["MajorTable"]["Did"],
            "Type": relationType.value
        }
        self.content["Relation"].append(relation)


    # def inRelationTo(self, uuid: str,dataType: DataClass):
    #     relation = {
    #         "Class": dataType.value,
    #         "Did": uuid,
    #         "Type": 1
    #     }
    #     self.content["Relation"].append(relation)

    # def setHead(self,head:str):
    #     self.content["Head"] = head

    def getContent(self):
        """
        :description: 获取内容
        :return: void
        """
        return self.content