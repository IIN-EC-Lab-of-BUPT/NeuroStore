from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class ParadigmBrowseQuery:
    """
    description: 范式主题下的浏览查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def ParadigmDetial(self,Did):
        """
        :description: 浏览指定范式的所有内容
        :param Did: str，范式的Did
        :return: ResultModel，指定范式的所有内容
        """
        getStr = "/query/paradigm/detail/{}".format(Did)
        r = self.requester.persistence_get(getStr)
        return r

