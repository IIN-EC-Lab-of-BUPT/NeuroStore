from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class PeopleConditionalQuery:
    """
    :description: 人员主题下的条件查询
    """
    def __init__(self,address: str):
        self.requester = request(address)

    def PeopleForGender(self,gender):
        """
        :description: 查找指定性别的人员
        :param gender: bool，性别
        :return: ResultModel，指定性别的人员信息
        """
        getStr = "/query/gender/{}".format(gender)
        r = self.requester.get(getStr)
        return r

    def PeopleForBirthday(self,Birthday):
        """
        :description: 查找指定生日的人员
        :param Birthday: datatime，生日
        :return: ResultModel，指定生日的人员信息
        """
        getStr = "/query/Birthday/{}".format(Birthday)
        r = self.requester.get(getStr)
        return r

    def PeopleForDescriptionLike(self,Description):
        """
        :description:  查找描述中含有指定字段的人员
        :param Description: str，描述
        :return: ResultModel，描述中含有指定字段的人员信息
        """
        getStr = "/query/Description/{}".format(Description)
        r = self.requester.get(getStr)
        return r

    # def PeopleForWeight(self,Weight):
    #     """
    #     :description: 查找指定体重的人员
    #     :param Weight: int，体重
    #     :return: ResultModel，指定体重的人员信息
    #     """
    #
    #     getStr = "/query/Weight/{}".format(Weight)
    #     r = self.requester.get(getStr)
    #     return r
    #
    # def PeopleForHight(self,Hight):
    #     """
    #     :description: 查找指定身高的人员
    #     :param Hight: int，身高
    #     :return: ResultModel，指定身高的人员信息
    #     """
    #
    #     getStr = "/query/Hight/{}".format(Hight)
    #     r = self.requester.get(getStr)
    #     return r
    #
    # def PeopleForLefthandedness(self,Lefthandedness):
    #     """
    #     :description: 查找是否左利手的人员
    #     :param Lefthandedness: bool，是否左利手
    #     :return: ResultModel，是否左利手的人员信息
    #     """
    #     getStr = "/query/Lefthandedness/{}".format(Lefthandedness)
    #     r = self.requester.get(getStr)
    #     return r