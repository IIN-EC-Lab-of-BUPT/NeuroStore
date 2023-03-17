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
@RequestMapping("query/paradigm")
public class ParadigmQueryController {

    @RequestMapping("/detail/{Did}")
    public List paradigmDetail(@PathVariable("Did") String did) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Paradigm", "Did", did, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/type/{type}")
    public List ParadigmForType(@PathVariable("type") String type) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("ParadigmP", "type", type, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/description/{content}")
    public List ParadigmForDescriptionLike(@PathVariable("content") String content) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("ParadigmP", "description", content, preparaedStatementUpdate);
        List list = parse(result);
        preparaedStatementUpdate.disconnect();
        return list;
    }
    @RequestMapping("/process/{processDid}")
    public List ParadigmForProcess(@PathVariable("processDid") String processDid) throws SQLException, IOException, ClassNotFoundException {

        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        ResultSet result = DbOperator.select("Process_Paradigm", "processDid", processDid, preparaedStatementUpdate);
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
