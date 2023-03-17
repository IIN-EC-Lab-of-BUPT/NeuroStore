package bupt.embemc.operators;

import bupt.embemc.App;
import bupt.embemc.singleton.MajorToMajorTableName;
import bupt.embemc.singleton.TempToBaseTables;
import bupt.embemc.utils.DbOperator;
import bupt.embemc.utils.JDBCUtils;
import bupt.embemc.utils.JSONReader;
import bupt.embemc.utils.PreparaedStatementUpdate;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = App.class)
class MessageHandleOperatorTest {

    @Value("${Jsonfile-Path}")
    String jsonFile;

    @Value("${APIconf-Path}")
    String confPath;

    @Autowired
    DbOperator dbOperator;

    @Autowired
    TempToBaseTables tempToBaseTables;

    @Autowired
    MajorToMajorTableName majorToMajorTableName;

    @Autowired
    JDBCUtils jdbcUtils;

    @Autowired
    FileWriterOperator fileWriterOperator;

    @Test
    void parse_map() throws SQLException, IOException, ClassNotFoundException {
        jdbcUtils.init();
        JSONReader.init(jsonFile,confPath);
        tempToBaseTables.init(JSONReader.getAPIMap(),JSONReader.getTempTables());
        majorToMajorTableName.init();
        PreparaedStatementUpdate ps = new PreparaedStatementUpdate();
        dbOperator.connect(1,ps);
//        MessageHandleOperator.init(tempToBaseTables,majorToMajorTableName,"c://test_delete");




//        Map map0_0 = new HashMap();
//        map0_0.put("CLASS","SubCompetition");
//        map0_0.put("*UUID","0_0");
//        map0_0.put("Name@Problem","ssvep");
//        MessageHandleOperator mo = new MessageHandleOperator((HashMap<String,String>) map0_0);
//
//        mo.proprecess((HashMap<String, String>) map0_0);
//        mo.run();
//
//        Map map0_1 = new HashMap();
//        map0_1.put("CLASS","SubCompetition");
//        map0_1.put("*UUID","0_1");
//        map0_1.put("Name@Problem","rsvp");
//        mo.proprecess((HashMap<String, String>) map0_1);
//        mo.run();
//
//
//        //block
//        Map map1_0 = new HashMap();
//        map1_0.put("CLASS","BLOCK");
//        map1_0.put("*UUID","1_0");
//        map1_0.put("UUID@SubCompetition","0_0");
//        map1_0.put("Name","1");
//        mo.proprecess((HashMap<String, String>) map1_0);
//        mo.run();
//        //
//        Map map_br1 = new HashMap();
//        map_br1.put("CLASS","BlockResult_BCI");
//        map_br1.put("*UUID","100");
//        map_br1.put("UUID@BLOCK","1_0");
//        map_br1.put("TeamName@Team","1");
//        map_br1.put("ID@Subject","1");
//        map_br1.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br1);
//        mo.run();
//
//        Map map_br2 = new HashMap();
//        map_br2.put("CLASS","BlockResult_BCI");
//        map_br2.put("*UUID","101");
//        map_br2.put("UUID@BLOCK","1_0");
//        map_br2.put("TeamName@Team","1");
//        map_br2.put("ID@Subject","2");
//        map_br2.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br2);
//        mo.run();
//
//        Map map_br3 = new HashMap();
//        map_br3.put("CLASS","BlockResult_BCI");
//        map_br3.put("*UUID","102");
//        map_br3.put("UUID@BLOCK","1_0");
//        map_br3.put("TeamName@Team","2");
//        map_br3.put("ID@Subject","1");
//        map_br3.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br3);
//        mo.run();
//
//        Map map_br4 = new HashMap();
//        map_br4.put("CLASS","BlockResult_BCI");
//        map_br4.put("*UUID","103");
//        map_br4.put("UUID@BLOCK","1_0");
//        map_br4.put("TeamName@Team","2");
//        map_br4.put("ID@Subject","2");
//        map_br4.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br4);
//        mo.run();
//
//        //-
//        Map map1_1 = new HashMap();
//        map1_1.put("CLASS","BLOCK");
//        map1_1.put("*UUID","1_1");
//        map1_1.put("UUID@SubCompetition","0_0");
//        map1_1.put("Name","2");
//        mo.proprecess((HashMap<String, String>) map1_1);
//        mo.run();
//        //
//        Map map_br11 = new HashMap();
//        map_br11.put("CLASS","BlockResult_BCI");
//        map_br11.put("*UUID","200");
//        map_br11.put("UUID@BLOCK","1_1");
//        map_br11.put("TeamName@Team","1");
//        map_br11.put("ID@Subject","1");
//        map_br11.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br11);
//        mo.run();
//
//        Map map_br22 = new HashMap();
//        map_br22.put("CLASS","BlockResult_BCI");
//        map_br22.put("*UUID","201");
//        map_br22.put("UUID@BLOCK","1_1");
//        map_br22.put("TeamName@Team","1");
//        map_br22.put("ID@Subject","2");
//        map_br22.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br22);
//        mo.run();
//
//        Map map_br33 = new HashMap();
//        map_br33.put("CLASS","BlockResult_BCI");
//        map_br33.put("*UUID","202");
//        map_br33.put("UUID@BLOCK","1_1");
//        map_br33.put("TeamName@Team","2");
//        map_br33.put("ID@Subject","1");
//        map_br33.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br33);
//        mo.run();
//
//        Map map_br44 = new HashMap();
//        map_br44.put("CLASS","BlockResult_BCI");
//        map_br44.put("*UUID","203");
//        map_br44.put("UUID@BLOCK","1_1");
//        map_br44.put("TeamName@Team","2");
//        map_br44.put("ID@Subject","2");
//        map_br44.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br44);
//        mo.run();
//
//        //-
//
//        Map map1_2 = new HashMap();
//        map1_2.put("CLASS","BLOCK");
//        map1_2.put("*UUID","1_2");
//        map1_2.put("UUID@SubCompetition","0_1");
//        map1_2.put("Name","1");
//        mo.proprecess((HashMap<String, String>) map1_2);
//        mo.run();
//        //
//        Map map_br111 = new HashMap();
//        map_br111.put("CLASS","BlockResult_BCI");
//        map_br111.put("*UUID","300");
//        map_br111.put("UUID@BLOCK","1_2");
//        map_br111.put("TeamName@Team","1");
//        map_br111.put("ID@Subject","1");
//        map_br111.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br111);
//        mo.run();
//
//        Map map_br222 = new HashMap();
//        map_br222.put("CLASS","BlockResult_BCI");
//        map_br222.put("*UUID","301");
//        map_br222.put("UUID@BLOCK","1_2");
//        map_br222.put("TeamName@Team","1");
//        map_br222.put("ID@Subject","2");
//        map_br222.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br222);
//        mo.run();
//
//        Map map_br333 = new HashMap();
//        map_br333.put("CLASS","BlockResult_BCI");
//        map_br333.put("*UUID","302");
//        map_br333.put("UUID@BLOCK","1_2");
//        map_br333.put("TeamName@Team","2");
//        map_br333.put("ID@Subject","1");
//        map_br333.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br333);
//        mo.run();
//
//        Map map_br444 = new HashMap();
//        map_br444.put("CLASS","BlockResult_BCI");
//        map_br444.put("*UUID","303");
//        map_br444.put("UUID@BLOCK","1_2");
//        map_br444.put("TeamName@Team","2");
//        map_br444.put("ID@Subject","2");
//        map_br444.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br444);
//        mo.run();
//        //-
//
//        Map map1_3 = new HashMap();
//        map1_3.put("CLASS","BLOCK");
//        map1_3.put("*UUID","1_3");
//        map1_3.put("UUID@SubCompetition","0_1");
//        map1_3.put("Name","2");
//        mo.proprecess((HashMap<String, String>) map1_3);
//        mo.run();
//        //
//        Map map_br1111 = new HashMap();
//        map_br1111.put("CLASS","BlockResult_BCI");
//        map_br1111.put("*UUID","400");
//        map_br1111.put("UUID@BLOCK","1_3");
//        map_br1111.put("TeamName@Team","1");
//        map_br1111.put("ID@Subject","1");
//        map_br1111.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br1111);
//        mo.run();
//
//        Map map_br2222 = new HashMap();
//        map_br2222.put("CLASS","BlockResult_BCI");
//        map_br2222.put("*UUID","401");
//        map_br2222.put("UUID@BLOCK","1_3");
//        map_br2222.put("TeamName@Team","1");
//        map_br2222.put("ID@Subject","2");
//        map_br2222.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br2222);
//        mo.run();
//
//        Map map_br3333 = new HashMap();
//        map_br3333.put("CLASS","BlockResult_BCI");
//        map_br3333.put("*UUID","402");
//        map_br3333.put("UUID@BLOCK","1_3");
//        map_br3333.put("TeamName@Team","2");
//        map_br3333.put("ID@Subject","1");
//        map_br3333.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br3333);
//        mo.run();
//
//        Map map_br4444 = new HashMap();
//        map_br4444.put("CLASS","BlockResult_BCI");
//        map_br4444.put("*UUID","403");
//        map_br4444.put("UUID@BLOCK","1_3");
//        map_br4444.put("TeamName@Team","2");
//        map_br4444.put("ID@Subject","2");
//        map_br4444.put("result","2.0");
//        mo.proprecess((HashMap<String, String>) map_br4444);
//        mo.run();
//        //-
//
//
//        //trial
//        Map map2_0 = new HashMap();
//        map2_0.put("CLASS","Trial");
//        map2_0.put("*UUID","2_0");
//        map2_0.put("UUID@BLOCK","1_0");
//        map2_0.put("Name","1");
//        mo.proprecess((HashMap<String, String>) map2_0);
//        mo.run();
//
//        Map map_tr = new HashMap();
//        map_tr.put("CLASS","TrialResult_BCI");
//        map_tr.put("*UUID","tr2_0");
//        map_tr.put("UUID@Trial","2_0");
//        map_tr.put("TeamName@Team","1");
//        map_tr.put("ID@Subject","1");
//        map_tr.put("score","1.0");
//        mo.proprecess((HashMap<String, String>) map_tr);
//        mo.run();
//
//        Map map_tr1 = new HashMap();
//        map_tr1.put("CLASS","TrialResult_BCI");
//        map_tr1.put("*UUID","tr2_1");
//        map_tr1.put("UUID@Trial","2_0");
//        map_tr1.put("TeamName@Team","1");
//        map_tr1.put("ID@Subject","2");
//        map_tr1.put("score","1.0");
//        mo.proprecess((HashMap<String, String>) map_tr1);
//        mo.run();
//
//        Map map_tr2 = new HashMap();
//        map_tr2.put("CLASS","TrialResult_BCI");
//        map_tr2.put("*UUID","tr2_2");
//        map_tr2.put("UUID@Trial","2_0");
//        map_tr2.put("TeamName@Team","2");
//        map_tr2.put("ID@Subject","1");
//        map_tr2.put("score","1.0");
//        mo.proprecess((HashMap<String, String>) map_tr2);
//        mo.run();
//
//        Map map_tr3 = new HashMap();
//        map_tr3.put("CLASS","TrialResult_BCI");
//        map_tr3.put("*UUID","tr2_3");
//        map_tr3.put("UUID@Trial","2_0");
//        map_tr3.put("TeamName@Team","2");
//        map_tr3.put("ID@Subject","2");
//        map_tr3.put("score","1.0");
//        mo.proprecess((HashMap<String, String>) map_tr3);
//        mo.run();
//
//        Map map2_1 = new HashMap();
//        map2_1.put("CLASS","Trial");
//        map2_1.put("*UUID","2_1");
//        map2_1.put("UUID@BLOCK","1_0");
//        map2_1.put("Name","2");
//        mo.proprecess((HashMap<String, String>) map2_1);
//        mo.run();
//
//        Map map2_2 = new HashMap();
//        map2_2.put("CLASS","Trial");
//        map2_2.put("*UUID","2_2");
//        map2_2.put("UUID@BLOCK","1_1");
//        map2_2.put("Name","1");
//        mo.proprecess((HashMap<String, String>) map2_2);
//        mo.run();
//
//        Map map2_3 = new HashMap();
//        map2_3.put("CLASS","Trial");
//        map2_3.put("*UUID","2_3");
//        map2_3.put("UUID@BLOCK","1_1");
//        map2_3.put("Name","2");
//        mo.proprecess((HashMap<String, String>) map2_3);
//        mo.run();
//
//        Map map2_4 = new HashMap();
//        map2_4.put("CLASS","Trial");
//        map2_4.put("*UUID","2_4");
//        map2_4.put("UUID@BLOCK","1_2");
//        map2_4.put("Name","1");
//        mo.proprecess((HashMap<String, String>) map2_4);
//        mo.run();
//
//        Map map2_5 = new HashMap();
//        map2_5.put("CLASS","Trial");
//        map2_5.put("*UUID","2_5");
//        map2_5.put("UUID@BLOCK","1_2");
//        map2_5.put("Name","2");
//        mo.proprecess((HashMap<String, String>) map2_5);
//        mo.run();
//
//        Map map2_6 = new HashMap();
//        map2_6.put("CLASS","Trial");
//        map2_6.put("*UUID","2_6");
//        map2_6.put("UUID@BLOCK","1_3");
//        map2_6.put("Name","1");
//        mo.proprecess((HashMap<String, String>) map2_6);
//        mo.run();
//
//        Map map2_7 = new HashMap();
//        map2_7.put("CLASS","Trial");
//        map2_7.put("*UUID","2_7");
//        map2_7.put("UUID@BLOCK","1_3");
//        map2_7.put("Name","2");
//        mo.proprecess((HashMap<String, String>) map2_7);
//        mo.run();
//
//
//
//        Map map_team1 = new HashMap();
//        map_team1.put("CLASS","Team");
//        map_team1.put("*TeamName","1");
//        mo.proprecess((HashMap<String, String>) map_team1);
//        mo.run();
//
//        Map map_team2 = new HashMap();
//        map_team2.put("CLASS","Team");
//        map_team2.put("*TeamName","2");
//        mo.proprecess((HashMap<String, String>) map_team2);
//        mo.run();
//
//        Map map_subject1 = new HashMap();
//        map_subject1.put("CLASS","Subject");
//        map_subject1.put("*ID","1");
//        mo.proprecess((HashMap<String, String>) map_subject1);
//        mo.run();
//
//        Map map_subject2 = new HashMap();
//        map_subject2.put("CLASS","Subject");
//        map_subject2.put("*ID","2");
//        mo.proprecess((HashMap<String, String>) map_subject2);
//        mo.run();









    }
}