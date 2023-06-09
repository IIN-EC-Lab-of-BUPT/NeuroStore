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
from platformAPI.eegplatformcommunicationmodule4py.HTTPCommunication.request import request
import requests
class GetDataHandler:
    """
    description: 非结构化数据获取
    """
    def __init__(self):
        pass


    # def getData(self,dataDid: str) -> bytes:
    #     """
    #     :description: 获取数据函数
    #     :param dataDid: str，数据标识
    #     :return: bytes，数据内容
    #     """
    #     self.content = b''
    #     self.receiver.subscribe(self.receiveTopic)
    #     message = {"Action":"getData",
    #                "content":dataDid}
    #     mes_conversed = json.dumps(message, sort_keys=True, indent=4,
    #                                separators=(',', ': '))
    #     self.sender.send(self.sendTopic, bytes(mes_conversed, encoding="utf-8"))
    #     while True:
    #         # 收信方法调用，当消费者在0.5s时限内能收到的消息时，consumeMsg为bytes()型，本例仅使用str()方法给出简单的反序列化示例，具体反序列化
    #         # 方法应由使用者决定
    #         consume_msg = self.receiver.receive()
    #         if len(consume_msg) == 4 | len(consume_msg == 5):
    #             if (str(consume_msg,encoding = "utf-8") == "start"):
    #                 self.startFlag = True
    #             elif(str(consume_msg,encoding = "utf-8") == "stop"):
    #                 self.startFlag = False
    #                 break
    #         else:
    #             if(self.startFlag):
    #                 self.content += consume_msg
    #     self.receiver.unsubscribe()
    #
    #     return self.content

    def getData(self,dataAddress: str) -> bytes:
        """
        :description: 获取数据函数
        :param dataDid: str，数据标识
        :return: bytes，数据内容
        """
        dataAddresssegment = dataAddress.split("%")
        file = dataAddresssegment[-1].split("/")[-1]

        dataType = file.split(".")[-1]

        url = "http://"+dataAddresssegment[0]+"/query/data/getRawData/{}".format(dataAddresssegment[1])
        response = requests.get(url)
        if response.status_code == 200:
            content_type = response.headers['content-type']
            content_length = int(response.headers['content-length'])
            with open(file, 'wb') as f:
                for chunk in response.iter_content(chunk_size=4096):
                    if chunk:
                        f.write(chunk)
