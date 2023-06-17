"""
author:chen yang
contact author:1783680741@qq.com
date:2023/1/10
description:

"""
from platformAPI.persistence.DataModel.Property import Property


class EMOTION(Property):
    """
    :description: 情感范式属性描述类
    """
    def __init__(self):
        super(EMOTION, self).__init__()
        self.content["CLASS"] = "EMOTIONP"

    def setEmotionType(self,emotionType: str):
        """
        :description: 设置情感类型
        :param emotionType: str，情感类型
        :return: void
        """
        self.content["MajorTable"]["EmotionType"] = emotionType

    def setDescription(self,description: str):
        """
        :description: 设置描述
        :param description: str，描述
        :return: void
        """
        self.content["MajorTable"]["Description"] = description