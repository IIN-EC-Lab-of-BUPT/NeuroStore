"""
author:CY
contact author:1783680741@qq.com
date:2023/3/30
description:

"""
from platformAPI.persistence.DataModel.Data.Data import Data
from platformAPI.persistence.DataModel.Data.Properties.BlockResult import BlockResult
from platformAPI.persistence.DataModel.People.People import People
from platformAPI.persistence.DataHandlers.DataSaveHandler import DataSaveHandler

#Initialize data memory
dataSaveHandler = DataSaveHandler("persistent-test","C:\\ZDeskTop\\ProVerRrepository\\230308_DataPersistencer_git\\NeuroStore1\\demos\\store\\producer-config.json")

#Select People model to store people information
people = People()
people.setName("xiaoming")

#Store the information to the system
while True:
    dataSaveHandler.save(people)

#Select the Data model to store data information
data = Data()
data.setName("ForTest")
data.addData(b"fakedata")

#Select the BlockResult property description class
blockResult = BlockResult()
blockResult.setResult(20)

#Mount the property description class
data.addProperty(blockResult)

#Create a link to people
data.inRelationTo(people)

#Storing person information

dataSaveHandler.save(data)

#Close the storage connection
# dataSaveHandler.close()