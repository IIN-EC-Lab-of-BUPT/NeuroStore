"""
author:CY
contact author:1783680741@qq.com
date:2023/2/12
description:

"""
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.CommunicationProducer import CommunicationProducer
from platformAPI.persistence.DataModel import DataModel
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.CommunicationConsumer import \
    CommunicationConsumer
import time
import json
class Manager:
    """
    :description: 子系统控制器的管理者，用于管理各个子系统
    """

    def __init__(self,topic: str, config_path: str,receiveTopic: str, receiveConfigPath: str):
        self.sender = CommunicationProducer(config_path)
        self.topic = topic
        self.receiveTopic = receiveTopic
        self.receiver = CommunicationConsumer(receiveConfigPath)
    def command(self,order):
        """
        :description: 直接发送命令，命令又用户自定义
        :param order:dict，命令内容
        :return: void
        """
        msg = json.dumps(order)
        self.sender.send(self.topic,bytes(msg, encoding="utf-8"))

    def stop(self,subSystemName: str):
        """
        :description: 向指定子系统发送暂停命令
        :param subSystemName: str，子系统的名字
        :return: void
        """
        order ={
            "TO":subSystemName,
            "ACTION":"stop"
        }
        msg = json.dumps(order)
        self.sender.send(self.topic, bytes(msg, encoding="utf-8"))
    def start(self,subSystemName: str):
        """
        :description: 向指定子系统发送开始命令
        :param subSystemName: str，子系统的名字
        :return: void
        """
        order = {
            "TO": subSystemName,
            "ACTION": "start"
        }
        msg = json.dumps(order)
        self.sender.send(self.topic, bytes(msg, encoding="utf-8"))
    def shutdown(self,subSystemName: str):
        """
        :description: 向指定子系统发送停止命令
        :param subSystemName: str，子系统的名字
        :return: void
        """
        order = {
            "TO": subSystemName,
            "ACTION": "shutdown"
        }
        msg = json.dumps(order)
        self.sender.send(self.topic, bytes(msg, encoding="utf-8"))

    def stopAll(self):
        """
        :description: 向所有子系统发送暂停命令
        :return: void
        """
        order = {
            "TO": "ALL",
            "ACTION": "stop"
        }
        msg = json.dumps(order)
        self.sender.send(self.topic, bytes(msg, encoding="utf-8"))
    def startAll(self):
        """
        :description: 向所有子系统发送开始命令
        :return: void
        """
        order = {
            "TO": "ALL",
            "ACTION": "start"
        }
        msg = json.dumps(order)
        self.sender.send(self.topic, bytes(msg, encoding="utf-8"))
    def shutdownAll(self):
        """
        :description: 向所有子系统发送停止命令
        :return: void
        """
        order = {
            "TO": "ALL",
            "ACTION": "shutdown"
        }
        msg = json.dumps(order)
        self.sender.send(self.topic, bytes(msg, encoding="utf-8"))

    def getState(self)->dict:
        """
        :description: 获取所有子系统的状态
        :return: dict，所有子系统的状态详情
        """
        order = {
            "TO": "SubsystemManager",
            "ACTION": "getStateList"
        }
        msg = json.dumps(order)
        self.sender.send(self.topic, bytes(msg, encoding="utf-8"))
        startTime = time.time()
        while True:
            consume_msg = self.receiver.receive()
            if consume_msg:
                return json.loads(consume_msg)
            time.sleep(0.1)
            if (time.time() - startTime > 5):
                e = Exception("查询超时！")
                raise e