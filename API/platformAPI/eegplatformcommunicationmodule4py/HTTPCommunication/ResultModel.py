class ResultModel:
    """
    :description: 封装的结果类型。其结构是一个二位数组，第一行表示属性的名字，下面几行表示具体内容，一行代表一条数据
    """
    def __init__(self,list):
        self.content = list

    def getColumNum(self):
        """
        :description: 获取属性的个数
        :return: int，属性的个数
        """

        return len(self.content[0])

    def getColumLabels(self):
        """
        :description: 获取属性的名字
        :return: list，装有所有属性名字的列表
        """
        return self.content[0]

    def getData(self):
        """
        :description: 获取所有数据内容
        :return: list[list]，装有所有数据的二位数组
        """
        return self.content[1:]

    def getLabelData(self,labelName):
        index = self.content[0].index(labelName)
        list =[]
        for i in range(1,len(self.content)):
            list.append(self.content[i][index])
        return list
