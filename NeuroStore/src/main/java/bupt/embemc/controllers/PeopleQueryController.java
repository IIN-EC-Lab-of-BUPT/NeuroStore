package bupt.embemc.controllers;


import bupt.embemc.utils.DbOperator;
import bupt.embemc.utils.PreparaedStatementUpdate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("query/people")
public class PeopleQueryController {
    @RequestMapping("/list")
    public List PeopleList() throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("People", "Did", "*",preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }

    @RequestMapping("/detail/{Did}")
    public List PeopleDetial(@PathVariable("Did") String did) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("People", "Did", did, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }

    @RequestMapping("/gender/{gender}")
    public List PeopleForGender(@PathVariable("gender") String gender) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("SubjectProperties","Did", "Gender", gender, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/Birthday/{Birthday}")
    public List PeopleForBirthday(@PathVariable("Birthday") String Birthday) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("SubjectProperties","Did", "Birthday", Birthday, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/Description/{Description}")
    public List PeopleForDescriptionLike(@PathVariable("Description") String Description) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("PeopleP","Did", "Description", Description, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/Weight/{Weight}")
    public List PeopleForWeight(@PathVariable("Weight") String Weight) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("PeopleP","Did", "Weight", Weight, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
//    @RequestMapping("/Hight/{Hight}")
//    public List PeopleForHight(@PathVariable("Hight") String Hight) throws SQLException, IOException, ClassNotFoundException {
//
//        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
//        preparaedStatementUpdate.connect(true,0);
//        ResultSet result = DbOperator.select("PeopleP","Did", "Hight", Hight, preparaedStatementUpdate);
//        List list = parse(result);
//        preparaedStatementUpdate.disconnect();
//        return list;
//    }
//    @RequestMapping("/Lefthandedness/{Lefthandedness}")
//    public List PeopleForLefthandedness(@PathVariable("Lefthandedness") String Lefthandedness) throws SQLException, IOException, ClassNotFoundException {
//
//        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
//        preparaedStatementUpdate.connect(true,0);
//        ResultSet result = DbOperator.select("PeopleP","Did", "Lefthandedness", Lefthandedness, preparaedStatementUpdate);
//        List list = parse(result);
//        preparaedStatementUpdate.disconnect();
//        return list;
//    }

    @RequestMapping("/process/{processDid}")
    public List PeopleForProcess(@PathVariable("processDid") String processDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process_People","Did", "processDid", processDid, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/data/{dataDid}")
    public List PeopleForData(@PathVariable("dataDid") String dataDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Data_People","Did", "dataDid", dataDid, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }



    public List parse(ResultSet result) throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int n = metaData.getColumnCount();
        ArrayList list = new ArrayList<>();
        ArrayList list_ColumnLabel = new ArrayList();
        for(int i  = 1;i <= n;i++){
            list_ColumnLabel.add(metaData.getColumnLabel(i));
        }
        list.add(list_ColumnLabel);
        while(result.next()){
            ArrayList list_value = new ArrayList();
            for(int i = 1;i <= n;i++){
                list_value.add(result.getString(i));
            }
            list.add(list_value);
        }
        return list;
    }
}
