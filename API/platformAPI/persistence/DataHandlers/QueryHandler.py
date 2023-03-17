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
import uuid
class QueryHandler:
    def __init__(self,sendTopic: str, receiveTopic: str, sendConfigPath: str, receiveConfigPath: str):
        """
        :description: 数据查询器
        :param sendTopic: 持久化服务Topic
        :param receiveTopic: 反馈Topic
        :param sendConfigPath: 配置地址
        :param receiveConfigPath: 配置地址
        """
        self.sendTopic = sendTopic
        self.receiveTopic = receiveTopic
        self.sender = CommunicationProducer(sendConfigPath)
        self.receiver = CommunicationConsumer(receiveConfigPath)
        self.startFlag = False
        self.id = uuid.uuid4()
    def query(self,sql: str):
        """
        :description: 查询函数
        :param sql: str，sql语句
        :return: list，查询 结果
        """
        self.receiver.subscribe(self.receiveTopic)
        message = {
            "Action":"Query",
            "ID":self.id,
            "content":sql
        }
        mes_conversed = json.dumps(message, sort_keys=True, indent=4,
                                   separators=(',', ': '))
        self.sender.send(self.sendTopic, bytes(mes_conversed, encoding="utf-8"))
        result = None
        while True:
            # 收信方法调用，当消费者在0.5s时限内能收到的消息时，consumeMsg为bytes()型，本例仅使用str()方法给出简单的反序列化示例，具体反序列化
            # 方法应由使用者决定
            consume_msg = self.receiver.receive()
            if len(consume_msg) > 4:
                jsonStr = str(consume_msg,encoding="utf-8")
                contentJson = json.loads(jsonStr)
                if(contentJson["ID"] == self.id):
                    result = contentJson["result"]
                    break
        self.receiver.unsubscribe()
        return result