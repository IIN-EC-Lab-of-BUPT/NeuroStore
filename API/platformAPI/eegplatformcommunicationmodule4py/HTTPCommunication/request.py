import requests

from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.ResultModel import ResultModel


class request:
    """
    :description: 基于HTTP协议的通信器
    """
    def __init__(self,address: str):
        self.address = "http://" + address
    def get(self,url: str):
        """
        :description: restful风格中的get接口
        :param url: str，目标通信的网址
        :return: bytes，查询结果
        """
        return requests.get(self.address+url).content

    def post(self,url: str,data: dict):
        """
        :description: restful风格中的post接口
        :param url: str，目标通信的网址
        :param data: 需要上传的内容
        :return: bytes，查询结果
        """

        r = requests.post(self.address+url,data)
        return r.content

    def persistence_get(self,url: str):
        """
        :description: 持久化系统的专用get接口
        :param url: str，目标通信的网址
        :return: ResultModel，查询结果
        """
        r = requests.post(self.address+url)
        s = r.decode(encoding="utf-8")
        s = s.replace("null", "\"\"")
        r_list = eval(s)
        return ResultModel(r_list)


if __name__ == '__main__':
    arr_str = "[[\"Did\",\"Name\",\"TimeStamp\"],[\"1\",\"\",\"2022-11-14 21:03:08\"]]"
    arr_list = eval(arr_str)

    print(len(arr_list))