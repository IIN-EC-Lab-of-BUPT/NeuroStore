"""
author:chen yang
contact author:1783680741@qq.com
date:2022/12/21
description:

"""

from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.CommunicationProducer import CommunicationProducer
from platformAPI.persistence.DataModel import DataModel
from platformAPI.eegplatformcommunicationmodule4py.communicationModuleImplement.CommunicationConsumer import \
    CommunicationConsumer
import uuid
import time
import json

class DataSaveHandler:
    """
    :description: 数据存储器
    """
    def __init__(self,topic: str, config_path: str,receiveTopic: str, receiveConfigPath: str):
        self.sender = CommunicationProducer(config_path)
        self.topic = topic
        self.receiveTopic = receiveTopic
        self.receiver = CommunicationConsumer(receiveConfigPath)

    def save(self,data: DataModel) -> bool:
        """
        :description: 数据发送函数
        :param data: DataModel，存储内容
        :return: bool，是否成功回执
        """
        head = uuid.uuid1()
        data.setHead(head)
        self.sender.send(self.topic, data.toBytes())
        return True
        # startTime = time.time()
        # while True:
        #     # 收信方法调用，当消费者在0.5s时限内能收到的消息时，consumeMsg为bytes()型，本例仅使用str()方法给出简单的反序列化示例，具体反序列化
        #     # 方法应由使用者决定
        #     consume_msg = self.receiver.receive()
        #     if len(consume_msg) == 4 | len(consume_msg == 5):
        #         backMessage = json.dumps(str(consume_msg,encoding = "utf-8"))
        #         if (backMessage["Head"] == head):
        #             if(backMessage["back"] == 'succeed'):
        #                 return True
        #             else:
        #                 return False
        #     time.sleep(0.1)
        #     if(time.time() - startTime > 5):
        #         return False

    def close(self):
        self.sender.close()
        self.receiver.close()

