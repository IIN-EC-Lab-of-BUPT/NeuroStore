import socket
import threading
import time

class server:
    def __init__(self,ipAddress: str, portNum:int):
        """
        :description: 通信的服务端
        :param ipAddress: 监听IP地址
        :param portNum: 监听端口
        """
        self.soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.soc.bind(ipAddress,portNum)
        self.listen(5)

    def receive(self):
        self.soc.recv(1024)
