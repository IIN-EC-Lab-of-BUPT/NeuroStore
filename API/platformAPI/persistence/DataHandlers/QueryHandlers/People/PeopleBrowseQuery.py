from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class PeopleBrowseQuery:
    """
    :description: 人员主题下的浏览查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def PeopleList(self):
        """
        :description: 查看所有人员
        :return: ResultModel，所有人员
        """
        getStr = "/query/people/list"
        r = self.requester.get(getStr)
        return r

    def PeopleDetial(self,Did):
        """
        :description: 浏览人员所有数据
        :param Did: str，人员Did
        :return: ResultModel，指定人员所有数据
        """
        getStr = "/query/detail/{}".format(Did)
        r = self.requester.get(getStr)
        return r



