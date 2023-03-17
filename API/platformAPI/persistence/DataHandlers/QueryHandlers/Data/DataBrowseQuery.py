from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request

class DataBrowseQuery:
    """
    :description: 数据主题浏览查询
    """
    def __init__(self,address: str):
        self.requester = request(address)
    def dataDetial(self,Did: str):
        """
        :description: 浏览数据的所有内容
        :param Did: str，Did
        :return: ResultModel，数据的所有内容
        """
        getStr = "/query/data/detail/{}".format(Did)
        c = self.requester.persistence_get(getStr)
        return c
        # print(c)
    def getData(self,path):
        getStr = "/query/data/getdata/{}".format(path)
        data = self.requester.persistence_get(getStr)
        return data

if __name__ == '__main__':
    pass
    # dataquery = DataBrowseQuery("lab:8081")
    # s = dataquery.dataDetial("1")
    # u = s.decode(encoding="utf-8")
    # res = u.replace("null","\"\"")
    # l = eval(res)
    # print(l[0])
    # a =
    # print(a)