package bupt.embemc.controllers;


import bupt.embemc.utils.JDBCUtils;
import bupt.embemc.utils.PreparaedStatementUpdate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("query")
public class QueryController {



    @RequestMapping("/score")
    public String queryScore() throws SQLException, IOException, ClassNotFoundException {
        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        preparaedStatementUpdate.query("select * from Data where Name=1;");
        preparaedStatementUpdate.disconnect();
        return "ok";

    }
    @RequestMapping("/process/{id}")
    public Map queryprocess(@PathVariable("id") Integer id, @RequestParam(value="type") String type) throws SQLException, IOException, ClassNotFoundException {
        System.out.println(id);
        System.out.println(type);
        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
        preparaedStatementUpdate.connect(true,0);
        preparaedStatementUpdate.query("select * from Data where Name=1;");
        preparaedStatementUpdate.disconnect();
        Map m = new HashMap<>();
        m.put("test","test");
        return m;

    }
}
