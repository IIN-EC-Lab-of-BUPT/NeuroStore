from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class ParadigmConditionalQuery:
    """
    :description: 范式主题下的条件查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def ParadigmForType(self,type):
        """
        :description: 查找指定类型的范式
        :param type: str，类型
        :return: ResultModel，指定类型的范式数据
        """
        getStr = "/query/paradigm/type/{}".format(type)
        r = self.requester.persistence_get(getStr)
        return r
    def ParadigmForDescriptionLike(self,content):
        """
        :description: 查找描述中含有指定字段的范式数据
        :param content: str，指定字段
        :return: ResultModel，描述中含有指定字段的范式数据
        """
        getStr = "/query/paradigm/description/{}".format(content)
        r = self.requester.persistence_get(getStr)
        return r