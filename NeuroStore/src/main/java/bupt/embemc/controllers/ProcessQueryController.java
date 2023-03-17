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
@RequestMapping("query/process")
public class ProcessQueryController {

    @RequestMapping("/detail/{processDid}")
    public List PeopleDetail(@PathVariable("processDid") String processDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process", "Did",processDid ,preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/list")
    public List processList() throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process", "Did", "*",preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/result/{processDid}")
    public List resutlForProces(@PathVariable("processDid") String processDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("ProcessP", "result","Did",processDid ,preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }

    @RequestMapping("/description/{content}")
    public List descriptionForProcess(@PathVariable("content") String content) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("ProcessP", "Did","description",content ,preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/fatherProcess/{processDid}")
    public List fatherProcessForProcess(@PathVariable("processDid") String processDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process_Process", "FatherDid","Did",processDid ,preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/sonsProcess/{processDid}")
    public List sonsProcessForProcess(@PathVariable("processDid") String processDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process_Process", "SonDid","Did",processDid ,preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/data/{dataDid}")
    public List processForData(@PathVariable("dataDid") String dataDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process_Data", "dataDid",dataDid ,preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/people/{peopleDid}")
    public List processForPeople(@PathVariable("peopleDid") String peopleDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process_People", "peopleDid",peopleDid ,preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/paradigm/{paradigmDid}")
    public List processForParadigm(@PathVariable("paradigmDid") String paradigmDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process_Paradigm", "paradigmDid",paradigmDid ,preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/device/{deviceDid}")
    public List processForDevice(@PathVariable("deviceDid") String deviceDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process_Device", "deviceDid",deviceDid ,preparaedStatementUpdate);
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
