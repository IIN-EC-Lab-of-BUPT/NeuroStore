package bupt.embemc.operators;

import bupt.embemc.App;
import bupt.embemc.receiver.MessageReceiver;
import bupt.embemc.singleton.MajorToMajorTableName;
import bupt.embemc.singleton.TempToBaseTables;
import bupt.embemc.utils.DbOperator;
import bupt.embemc.utils.JDBCUtils;
import bupt.embemc.utils.JSONReader;
import bupt.embemc.utils.PreparaedStatementUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = App.class)
public class DbInitOperatorTest {
    @Autowired
    private DbOperator DbO;

    @Autowired
    TempToBaseTables tempToBaseTables;

    @Autowired
    MajorToMajorTableName majorToMajorTableName;

    @Autowired
    private DbInitOperator dbInitOperator;


    @Value("${Jsonfile-Path}")
    String jsonFile;

    @Value("${APIconf-Path}")
    String confPath;
    @Autowired
    JDBCUtils jdbcUtils;
    @Test
    public void testgeneretor() throws SQLException, IOException, ClassNotFoundException {
        jdbcUtils.init();
        JSONReader.init(jsonFile,confPath);
        tempToBaseTables.init(JSONReader.getAPIMap(),JSONReader.getTempTables());
        majorToMajorTableName.init();
        PreparaedStatementUpdate ps = new PreparaedStatementUpdate();
        DbO.connect(1,ps);


        dbInitOperator.createTables(DbO,ps,tempToBaseTables,majorToMajorTableName);
//        String function1_0 = "DROP PROCEDURE IF EXISTS function1;";
//        DbOperator.ps.update(function1_0);
//        String function1 =
//                "DELIMITER $$\n" +
//                "CREATE PROCEDURE function1\n" +
//                "(   IN teamName CHAR(60)," +
//                "    IN subcompetitionName CHAR(60)" +
//                ")" +
//                "BEGIN" +
//                "    DECLARE subcompetition INT;" +
//                "DECLARE team INT;" +
//                "select a.Did  into subcompetition from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;" +
//
//                "select a.Did into team from People a inner join People_TeamP b on a.Did=b.PeopleDid inner join TeamP c on b.TeamPDid = c.Did where c.teamname=teamname;" +
//                "select sum(c.result) from" +
//                "    (select a.Did FROM `data` a " +
//                "INNER JOIN data_people b on a.Did=b.DataDid " +
//                "INNER JOIN data_processes c on c.DataDid=a.Did " +
//                "INNER JOIN " +
//                "(select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetition /*Blocks*/) t1" +
//                "on c.processesDid=t1.Did where b.PeopleDid=team) a " +
//                "INNER JOIN data_blockresult b on a.Did = b.DataDid  " +
//                "INNER JOIN blockresult c on b.BlockresultDid = c.Did;" +
//                "END $$\n";
//        DbOperator.ps.update(function1);
//
//        String function2_0 = "DROP PROCEDURE IF EXISTS function2;";
//        DbOperator.ps.update(function2_0);
//        String function2 =
//                "DELIMITER $$\n" +
//                "CREATE PROCEDURE function2\n" +
//                "(\n" +
//                "    IN subjectID BIGINT,\n" +
//                "    IN subcompetitionName CHAR(60)\n" +
//                ")\n" +
//                "BEGIN\n" +
//                "    DECLARE subcompetitionDid INT;\n" +
//                "\t\tDECLARE subjectDid INT;\n" +
//                "\t\tselect a.Did  into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
//                "\n" +
//                "\t\tselect a.Did into subjectDid from People a inner join People_SubjectP b on a.Did=b.PeopleDid inner join SubjectP c on b.SubjectPDid = c.Did where c.ID=subjectID;\n" +
//                "\t\t\n" +
//                "\t\tselect sum(c.result) from\n" +
//                "    (select a.Did FROM `data` a \n" +
//                "\t\tINNER JOIN data_people b on a.Did=b.DataDid \n" +
//                "\t\tINNER JOIN data_processes c on c.DataDid=a.Did \n" +
//                "\t\t\tINNER JOIN \n" +
//                "\t\t(select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid /*Blocks*/) t1 on c.processesDid=t1.Did where b.PeopleDid=subjectDid) a \n" +
//                "\t\tINNER JOIN data_blockresult b on a.Did = b.DataDid  \n" +
//                "\t\tINNER JOIN blockresult c on b.BlockresultDid = c.Did;\n" +
//                "\n" +
//                "\n" +
//                "\n" +
//                "END $$\n" +
//                "DELIMITER ;";
//        String function3_0 = "DROP PROCEDURE IF EXISTS function3;";
//        DbOperator.ps.update(function3_0);
//        String function3 =
//                "DELIMITER $$\n" +
//                "CREATE PROCEDURE function3\n" +
//                "(\n" +
//                "    IN subjectID BIGINT,\n" +
//                "    IN subcompetitionName CHAR(60),\n" +
//                "\t\tIN teamName CHAR(60)\n" +
//                ")\n" +
//                "BEGIN\n" +
//                "    DECLARE subcompetitionDid INT;\n" +
//                "\t\tDECLARE subjectDid INT;\n" +
//                "\t\tDECLARE teamDid INT;\n" +
//                "\t\tselect a.Did  into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
//                "\n" +
//                "\t\tselect a.Did into subjectDid from People a inner join People_SubjectP b on a.Did=b.PeopleDid inner join SubjectP c on b.SubjectPDid = c.Did where c.ID=subjectID;\n" +
//                "\t\t\n" +
//                "\t\tselect a.Did into teamDid from People a inner join People_TeamP b on a.Did=b.PeopleDid inner join TeamP c on b.TeamPDid = c.Did where c.teamname=teamName;\n" +
//                "\t\t\n" +
//                "\t\tselect c.result, c.timestamp from\n" +
//                "\t\t(\n" +
//                "\t\tselect a.Did  from\n" +
//                "    (select a.Did Did FROM `data` a \n" +
//                "\t\t\t\tINNER JOIN data_people b on a.Did=b.DataDid \n" +
//                "\t\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did \n" +
//                "\t\t\t\tINNER JOIN \n" +
//                "\t\t\t\t(select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid /*Blocks*/\n" +
//                "\t\t\t\t) t1 on c.processesDid=t1.Did where b.PeopleDid=subjectDid\n" +
//                "\t\t) a \n" +
//                "\t\tINNER JOIN\n" +
//                "\t\t(select a.Did Did FROM `data` a \n" +
//                "\t\t\t\tINNER JOIN data_people b on a.Did=b.DataDid \n" +
//                "\t\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did \n" +
//                "\t\t\t\tINNER JOIN \n" +
//                "\t\t\t\t(select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = teamDid /*Blocks*/\n" +
//                "\t\t\t\t) t1 on c.processesDid=t1.Did where b.PeopleDid=teamDid\n" +
//                "\t\t) b  on a.Did = b.Did\n" +
//                "\t\t\n" +
//                "\t\t) a \t\t\n" +
//                "\t\tINNER JOIN data_blockresult b on a.Did = b.DataDid  \n" +
//                "\t\tINNER JOIN blockresult c on b.BlockresultDid = c.Did;\n" +
//                "END $$\n" ;
//
//        String function4_0 = "DROP PROCEDURE IF EXISTS function4;";
//        DbOperator.ps.update(function4_0);
//        String function4 =
//                "DELIMITER $$\n" +
//                "CREATE PROCEDURE function4\n" +
//                "(\n" +
//                "    IN subjectID BIGINT,\n" +
//                "    IN subcompetitionName CHAR(60),\n" +
//                "\t\tIN teamName CHAR(60),\n" +
//                "\t\tIN blockName CHAR(60)\n" +
//                ")\n" +
//                "BEGIN\n" +
//                "    DECLARE subcompetitionDid INT;\n" +
//                "\t\tDECLARE subjectDid INT;\n" +
//                "\t\tDECLARE teamDid INT;\n" +
//                "\t\t\n" +
//                "\t\tselect a.Did  into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
//                "\n" +
//                "\t\tselect a.Did into subjectDid from People a inner join People_SubjectP b on a.Did=b.PeopleDid inner join SubjectP c on b.SubjectPDid = c.Did where c.ID=subjectID;\n" +
//                "\t\t\n" +
//                "\t\tselect a.Did into teamDid from People a inner join People_TeamP b on a.Did=b.PeopleDid inner join TeamP c on b.TeamPDid = c.Did where c.teamname=teamName;\n" +
//                "\t\t\n" +
//                "\t\tselect d.score score FROM\n" +
//                "\t\t\t\t(\n" +
//                "\t\t\t\t\t\tselect a.Did Did  from `data` a \n" +
//                "\t\t\t\t\t\tINNER JOIN data_processes b on a.Did=b.DataDid\n" +
//                "\t\t\t\t\t\tINNER JOIN data_people c on c.DataDid = a.Did\n" +
//                "\t\t\t\t\t\tINNER JOIN\n" +
//                "\t\t\t\t\t\t(\n" +
//                "\t\t\t\t\t\t\t\tselect a.Did Did from Processes a\n" +
//                "\t\t\t\t\t\t\t\tINNER JOIN\n" +
//                "\t\t\t\t\t\t\t\tprocesses_processes b on a.Did = b.SubjectProcessesDid\n" +
//                "\t\t\t\t\t\t\t\tINNER JOIN\n" +
//                "\t\t\t\t\t\t\t\t(select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid and a.name = blockname /*Block*/\n" +
//                "\t\t\t\t\t\t\t\t) c on b.objectProcessesDid = c.Did\n" +
//                "\t\t\t\t\t\t) d on b.processesDid = d.Did \n" +
//                "\t\t\t\t\t\twhere c.PeopleDid = subjectDid\n" +
//                "\t\t\t\t) a\n" +
//                "\t\t\t\tINNER JOIN\n" +
//                "\t\t\t\t(\n" +
//                "\t\t\t\t\t\tselect a.Did Did  from `data` a \n" +
//                "\t\t\t\t\t\tINNER JOIN data_processes b on a.Did=b.DataDid\n" +
//                "\t\t\t\t\t\tINNER JOIN data_people c on c.DataDid = a.Did\n" +
//                "\t\t\t\t\t\tINNER JOIN\n" +
//                "\t\t\t\t\t\t(\n" +
//                "\t\t\t\t\t\t\t\tselect a.Did Did from Processes a\n" +
//                "\t\t\t\t\t\t\t\tINNER JOIN\n" +
//                "\t\t\t\t\t\t\t\tprocesses_processes b on a.Did = b.SubjectProcessesDid\n" +
//                "\t\t\t\t\t\t\t\tINNER JOIN\n" +
//                "\t\t\t\t\t\t\t\t(select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid and a.name = blockname /*Block*/\n" +
//                "\t\t\t\t\t\t\t\t) c on b.objectProcessesDid = c.Did\n" +
//                "\t\t\t\t\t\t) d on b.processesDid = d.Did \n" +
//                "\t\t\t\t\t\twhere c.PeopleDid = teamDid\n" +
//                "\t\t\t\t) b on a.Did = b.Did\n" +
//                "\t\t\t\tINNER JOIN data_trialresult c on c.DataDid=a.Did\n" +
//                "\t\t\t\tINNER JOIN trialresult d on d.Did = c.TrialResultDid;\n" +
//                "\n" +
//                "END $$\n";
//
////        DbOperator.ps.update(function1);
////        DbOperator.ps.update(function2);
////        DbOperator.ps.update(function3);
////        DbOperator.ps.update(function4);
////        DbOperator.ps.update("DELIMITER ;");
    }


}