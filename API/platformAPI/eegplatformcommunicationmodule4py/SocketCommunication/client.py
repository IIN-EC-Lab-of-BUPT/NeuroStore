import socket
import time



class client:
    def __init__(self,ipAdress: str,port: int):
        """
        :description: 通信的客户端
        :param ipAdress: 服务端的IP地址
        :param port: 服务端的端口号
        """
        self.soc = socket.socket(socket.AF_INET, socket.SOCK__STREAM)
        self.soc.connect(ipAdress,port)
    def send(self,content: str):
        """
        :description: 发送数据
        :param content: 发送数据的内容
        :return:
        """
        self.soc.send(content.encode())

    def close(self):
        """
        :description: 关闭连接，释放资源
        """
        self.soc.close()