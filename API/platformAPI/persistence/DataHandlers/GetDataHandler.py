"""
author:chen yang
contact author:1783680741@qq.com
date:2022/12/21
description:

"""
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.CommunicationConsumer import \
    CommunicationConsumer
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.CommunicationProducer import \
    CommunicationProducer
import json

class GetDataHandler:
    """
    description: 非结构化数据获取
    """
    def __init__(self,sendTopic: str, receiveTopic: str, sendConfigPath: str, receiveConfigPath: str):
        self.sendTopic = sendTopic
        self.receiveTopic = receiveTopic
        self.sender = CommunicationProducer(sendConfigPath)
        self.receiver = CommunicationConsumer(receiveConfigPath)
        self.startFlag = False
        self.content = b''

    def getData(self,dataDid: str) -> bytes:
        """
        :description: 获取数据函数
        :param dataDid: str，数据标识
        :return: bytes，数据内容
        """
        self.content = b''
        self.receiver.subscribe(self.receiveTopic)
        message = {"Action":"getData",
                   "content":dataDid}
        mes_conversed = json.dumps(message, sort_keys=True, indent=4,
                                   separators=(',', ': '))
        self.sender.send(self.sendTopic, bytes(mes_conversed, encoding="utf-8"))
        while True:
            # 收信方法调用，当消费者在0.5s时限内能收到的消息时，consumeMsg为bytes()型，本例仅使用str()方法给出简单的反序列化示例，具体反序列化
            # 方法应由使用者决定
            consume_msg = self.receiver.receive()
            if len(consume_msg) == 4 | len(consume_msg == 5):
                if (str(consume_msg,encoding = "utf-8") == "start"):
                    self.startFlag = True
                elif(str(consume_msg,encoding = "utf-8") == "stop"):
                    self.startFlag = False
                    break
            else:
                if(self.startFlag):
                    self.content += consume_msg
        self.receiver.unsubscribe()

        return self.content