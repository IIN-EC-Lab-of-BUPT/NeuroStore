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
import java.util.Map;

@RestController
@RequestMapping("query/data")
public class DataQueryController {
    @RequestMapping("/detail/{Did}")
    public List dataDetail(@PathVariable("Did") String did) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Data", "Did", did, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/type/{type}")
    public List dataForType(@PathVariable("type") String type) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("DataTypes", "type", type, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/frequence/{frequence}")
    public List dataForFrequence(@PathVariable("frequence") String frequence) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("EEGDataP", "SampleFrequency", frequence, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/channel/{channel}")
    public List dataForChannel(@PathVariable("channel") String channel) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("DataP", "channel", channel, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/channelNum/{channelNum}")
    public List dataForChannelNum(@PathVariable("channelNum") String channelNum) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("EEGDataP", "ClumnNum", channelNum, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/process/{processDid}")
    public List dataForProcess(@PathVariable("processDid") String processDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Data_People", "processDid", processDid, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/People/{PeopleDid}")
    public List dataForPeople(@PathVariable("PeopleDid") String PeopleDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Data_Processes", "processDid", PeopleDid, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/Device/{deviceDid}")
    public List dataForDevice(@PathVariable("deviceDid") String deviceDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Data_Devices", "processDid", deviceDid, preparaedStatementUpdate);
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
