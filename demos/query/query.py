from platformAPI.persistence.DataHandlers.QueryHandlers.Data.DataBrowseQuery import DataBrowseQuery
from platformAPI.persistence.DataHandlers.QueryHandlers.People.PeopleConditionalQuery import PeopleConditionalQuery
from platformAPI.persistence.DataHandlers.QueryHandlers.Data.DataJointQuery import DataJointQuery
from datetime import datetime

#Initialize the query interface
address = "localhost:8081"
pc = PeopleConditionalQuery(address)
dj = DataJointQuery(address)
db = DataBrowseQuery(address)

#Find all people whose age is 21 and get their Did
people_age_21 = pc.PeopleForBirthday(datetime("2002"))
people_did = people_age_21.getLabelData("Did")

datafilePath = []
for i in people_did:
     #Find all people with age 21 generated data
     datafilePath.append(dj.dataForPeople(i).getLabelData("Path"))
for i in datafilePath:
    for path in i:
        #Get all the data
        DataBrowseQuery.getData(path)