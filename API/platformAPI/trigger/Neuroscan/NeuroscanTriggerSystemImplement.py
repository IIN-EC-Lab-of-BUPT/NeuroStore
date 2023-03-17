from platformAPI.trigger.TriggerSystemInterface import TriggerSystemInterface
from ctypes import *
import time
import sys
import os
import threading


class NeuroscanTriggerSystemImplement(TriggerSystemInterface):
    """
    :param port: neuroscan并口端口号,如:16376
    """
    def __init__(self, port):

        # 端口
        self.port = port

        # 初始输出trigger
        self.initialOutCode = 0

        self.event = threading.Event()
        self.runFlag = True
        self.timer = threading.Thread(target=self.__reset, args=(self.event,))

        # dll路径
        currentPath = os.path.dirname(__file__)
        self.dllPath = os.path.join(os.path.dirname(currentPath), r'inpoutx64.dll')
        self.parallelPort = windll.LoadLibrary(self.dllPath)

    def open(self):
        """
        :return: none
        """
        if self.parallelPort.IsInpOutDriverOpen():
            print('InpOut driver is opened successfully')
        self.parallelPort.DlPortWritePortUchar(self.port, self.initialOutCode)
        time.sleep(0.02)
        self.timer.start()

    def send(self, event):
        """
        :param event: 输入想要输出的trigger值，trigger值应为1~255之间的整数
        :return: None
        """
        try:
            self.parallelPort.DlPortWritePortUchar(self.port, event)
            self.event.set()
        except Exception as e:
            print(e)

    def close(self):
        """
        关闭端口，停止线程
        :return: None
        """
        self.runFlag = False
        self.event.set()

    def __reset(self, event):
        while True:
            event.wait()
            if not self.runFlag:
                break
            time.sleep(0.02)
            self.parallelPort.DlPortWritePortUchar(self.port, self.initialOutCode)
            event.clear()
