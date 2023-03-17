package bupt.embemc.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import java.sql.ResultSet;
import com.alibaba.fastjson.JSONObject;



import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;



public class PreparseTest {
//    @Test
//    public void testSave() throws SQLException, IOException, ClassNotFoundException {
//        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
//        preparaedStatementUpdate.connect();
//        String a = "update `b`(\n" +
//                "   `title`,\n" +
//                "   `author`,\n" +
//                "   `submission_date`\n" +
//                ")" +
//                "values" +
//                "(\"mysql\",\"cy\",NOW());";
//        System.out.println(preparaedStatementUpdate.update(a));;
//    }
//    @Test
//    public void testJsonArray(){
//        JSONArray jsonArray = JSON.parseArray("[\"qfqe\",\"b\",\"1\"]");
////        System.out.println(JSON.parseObject("{a:adf}"));
//        System.out.println(jsonArray.getString(2));
//
//    }
//    @Test
//    public void testQuery() throws SQLException, IOException, ClassNotFoundException {
//        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
//        preparaedStatementUpdate.connect();
//        String a ="call function1(\"1\",\"ssvep\");";
//        ResultSet resultSet = preparaedStatementUpdate.query(a);
//        resultSet.next();
//        System.out.println(resultSet.getInt("sum(c.result)"));
//    }
//    @Test
//    public void testComment() throws SQLException, IOException, ClassNotFoundException {
//        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
//        preparaedStatementUpdate.connect();
////        String s = " CREATE TABLE runoob_tbl2(\n" +
////                "    runoob_id INT NOT NULL AUTO_INCREMENT COMMENT '1',\n" +
////                "   runoob_title VARCHAR(100) NOT NULL COMMENT '2',\n" +
////                "   runoob_author VARCHAR(40) NOT NULL COMMENT '3',\n" +
////                "    submission_date DATE  COMMENT '4',\n" +
////                "    PRIMARY KEY ( runoob_id )\n" +
////                "    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sd'";
//        String s = "create table if not exists `巡逻` (`*ID` INT COMMENT 'INT', `EndTime` BIGINT COMMENT 'BIGINT', `StartTime` BIGINT COMMENT 'BIGINT', `Name` CHAR(60) COMMENT 'CHAR(60)', `TimeStamp` DATETIME COMMENT 'Time when data was last updated', primary key (`*ID` )) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='巡逻';";
//        preparaedStatementUpdate.update(s);
//    }
//    @Test
//    public void testCreatProcess() throws SQLException, IOException, ClassNotFoundException {
//        PreparaedStatementUpdate preparaedStatementUpdate = new PreparaedStatementUpdate();
//        preparaedStatementUpdate.connect();
//        String sql1_0 = "DROP PROCEDURE IF EXISTS score_team_subcompetition";
//        String sql1_1 = "CREATE PROCEDURE score_team_subcompetition\n" +
//                "(\n" +
//                "    IN teamName CHAR(60),\n" +
//                "    IN subcompetitionName CHAR(60)\n" +
//                ")\n" +
//                "BEGIN\n" +
//                "    DECLARE subcompetition INT;\n" +
//                "    DECLARE team INT;\n" +
//                "    select a.Did into subcompetition from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;/*subcompetitionDid*/\n" +
//                "\n" +
//                "    select a.Did into team from People a inner join People_TeamP b on a.Did=b.PeopleDid inner join TeamP c on b.TeamPDid = c.Did where c.teamname=teamname; /*teamDid*/\n" +
//                "\t\n" +
//                "    select sum(c.result)/count(c.result) score from /*所有Block平均成绩*/\n" +
//                "\t\t(select A.Did FROM /*所需dataDid*/\n" +
//                "\t\t\t`data` a\n" +
//                "\t\t\tINNER JOIN data_people b on a.Did=b.DataDid\n" +
//                "\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did\n" +
//                "\t\t\tINNER JOIN\t\n" +
//                "\t\t\t(SELECT  DISTINCT (FIRST_VALUE(Did) over(partition by name order by TimeStamp desc ) )as Did from /*Blocks*/\t/*Blocks' Did,且根据名字去重*/\n" +
//                "\t\t\t\t(select a.Did Did,a.name name, a.TimeStamp timestamp from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetition) a /*Blocks' Did*/\n" +
//                "\t\t\t) t1 \n" +
//                "\t\t\ton c.processesDid=t1.Did \n" +
//                "\t\t\twhere b.PeopleDid=team\n" +
//                "\t\t) a\t\t\t\t\t\t   \n" +
//                "        INNER JOIN data_blockresult b on a.Did = b.DataDid\n" +
//                "        INNER JOIN blockresult c on b.BlockresultDid = c.Did;\n" +
//                "END;";
//        String sql2_0="DROP PROCEDURE IF EXISTS  score_subject_subcompetition ";
//        String sql2_1="CREATE PROCEDURE  score_subject_subcompetition\n" +
//                "(\n" +
//                "    IN subjectID BIGINT,\n" +
//                "    IN subcompetitionName CHAR(60)\n" +
//                ")\n" +
//                "BEGIN\n" +
//                "    DECLARE subcompetitionDid INT;\n" +
//                "    DECLARE subjectDid INT;\n" +
//                "    select a.Did into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
//                "\n" +
//                "    select a.Did into subjectDid from People a inner join People_SubjectP b on a.Did=b.PeopleDid inner join SubjectP c on b.SubjectPDid = c.Did where c.ID=subjectID;\n" +
//                "\t\n" +
//                "\tselect sum(c.result)/count(c.result) score from \n" +
//                "\t\t(select A.Did FROM \n" +
//                "\t\t\t`data` a\n" +
//                "\t\t\tINNER JOIN data_people b on a.Did=b.DataDid\n" +
//                "\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did\n" +
//                "\t\t\tINNER JOIN\t\n" +
//                "\t\t\t(SELECT  DISTINCT (FIRST_VALUE(Did) over(partition by name order by TimeStamp desc ) )as Did from /*Blocks*/\t/*Blocks' Did,且根据名字去重*/\n" +
//                "\t\t\t\t(select a.Did Did,a.name name, a.TimeStamp timestamp from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid) a /*Blocks' Did*/\n" +
//                "\t\t\t) t1 \n" +
//                "\t\t\ton c.processesDid=t1.Did \n" +
//                "\t\t\twhere b.PeopleDid=subjectDid\n" +
//                "\t\t) a\t\t\t\t\t\t   \n" +
//                "        INNER JOIN data_blockresult b on a.Did = b.DataDid\n" +
//                "        INNER JOIN blockresult c on b.BlockresultDid = c.Did;\n" +
//                "    \n" +
//                "END;";
//        String sql3_0="DROP PROCEDURE IF EXISTS score_subject_team_subcompetion";
//        String sql3_1="CREATE PROCEDURE score_subject_team_subcompetion\n" +
//                "(\n" +
//                "    IN subjectID BIGINT,\n" +
//                "\tIN teamName CHAR(60),\n" +
//                "\tIN subcompetitionName CHAR(60)\n" +
//                ")\n" +
//                "BEGIN\n" +
//                "    DECLARE subcompetitionDid INT;\n" +
//                "\tDECLARE subjectDid INT;\n" +
//                "\tDECLARE teamDid INT;\n" +
//                "\tselect a.Did  into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
//                "\n" +
//                "\tselect a.Did into subjectDid from People a inner join People_SubjectP b on a.Did=b.PeopleDid inner join SubjectP c on b.SubjectPDid = c.Did where c.ID=subjectID;\n" +
//                "\t\t\n" +
//                "\tselect a.Did into teamDid from People a inner join People_TeamP b on a.Did=b.PeopleDid inner join TeamP c on b.TeamPDid = c.Did where c.teamname=teamName;\n" +
//                "\t\t\n" +
//                "\tselect a.name,c.result from\n" +
//                "\t\t(select a.Did, a.name  from\n" +
//                "\t\t\t(select a.Did Did, t1.name name FROM \n" +
//                "\t\t\t\t`data` a \n" +
//                "\t\t\t\tINNER JOIN data_people b on a.Did=b.DataDid \n" +
//                "\t\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did \n" +
//                "\t\t\t\tINNER JOIN \n" +
//                "\t\t\t\t(SELECT  distinct name,(FIRST_VALUE(Did) over(partition by name order by TimeStamp desc) )as Did  from /*Blocks*/\t/*Blocks' Did,且根据名字去重，带名字*/\n" +
//                "\t\t\t\t\t(select a.Did Did,a.name name, a.TimeStamp timestamp from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid) a /*Blocks' Did*/\n" +
//                "\t\t\t\t) t1 on c.processesDid=t1.Did \n" +
//                "\t\t\t\twhere b.PeopleDid=subjectDid\n" +
//                "\t\t\t) a \n" +
//                "\t\t\tINNER JOIN\n" +
//                "\t\t\t(select a.Did Did FROM \n" +
//                "\t\t\t\t`data` a \n" +
//                "\t\t\t\tINNER JOIN data_people b on a.Did=b.DataDid \n" +
//                "\t\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did \n" +
//                "\t\t\t\tINNER JOIN \n" +
//                "\t\t\t\t(SELECT  distinct name,(FIRST_VALUE(Did) over(partition by name order by TimeStamp desc) )as Did  from\t/*Blocks' Did,且根据名字去重,带名字*/\n" +
//                "\t\t\t\t\t(select a.Did Did,a.name name, a.TimeStamp timestamp from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid) a /*Blocks' Did*/\n" +
//                "\t\t\t\t) t1 on c.processesDid=t1.Did \n" +
//                "\t\t\t\twhere b.PeopleDid=teamDid\n" +
//                "\t\t\t) b on a.Did = b.Did\n" +
//                "\t\t) a \t\t\n" +
//                "\t\tINNER JOIN data_blockresult b on a.Did = b.DataDid  \n" +
//                "\t\tINNER JOIN blockresult c on b.BlockresultDid = c.Did;\n" +
//                "END;";
//        String sql4_0="DROP PROCEDURE IF EXISTS trialDetials_subject_subcompetition_team_block";
//        String sql4_1="CREATE PROCEDURE trialDetials_subject_subcompetition_team_block\n" +
//                "(\n" +
//                "    IN subjectID BIGINT,\n" +
//                "    IN subcompetitionName CHAR(60),\n" +
//                "\tIN teamName CHAR(60),\n" +
//                "\tIN blockName CHAR(60)\n" +
//                ")\n" +
//                "BEGIN\n" +
//                "    DECLARE subcompetitionDid INT;\n" +
//                "\t\tDECLARE subjectDid INT;\n" +
//                "\t\tDECLARE teamDid INT;\n" +
//                "\t\t\n" +
//                "\t\tselect a.Did into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName; /*赛题*/\n" +
//                "\n" +
//                "\t\tselect a.Did into subjectDid from People a inner join People_SubjectP b on a.Did=b.PeopleDid inner join SubjectP c on b.SubjectPDid = c.Did where c.ID=subjectID; /*被试*/\n" +
//                "\t\t\n" +
//                "\t\tselect a.Did into teamDid from People a inner join People_TeamP b on a.Did=b.PeopleDid inner join TeamP c on b.TeamPDid = c.Did where c.teamname=teamName; /*赛队*/\n" +
//                "\t\t\n" +
//                "\t\tselect b.name, d.score score,d.resultNum result, d.usedTime usedTime FROM\n" +
//                "\t\t\t(select a.Did Did  from \n" +
//                "\t\t\t\t`data` a \n" +
//                "\t\t\t\tINNER JOIN data_processes b on a.Did=b.DataDid\n" +
//                "\t\t\t\tINNER JOIN data_people c on c.DataDid = a.Did\n" +
//                "\t\t\t\tINNER JOIN\n" +
//                "\t\t\t\t(select a.Did Did from \n" +
//                "\t\t\t\t\tProcesses a\n" +
//                "\t\t\t\t\tINNER JOIN processes_processes b on a.Did = b.SubjectProcessesDid\n" +
//                "\t\t\t\t\tINNER JOIN (select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid and a.name = blockname order by a.TimeStamp desc limit 1/*Block*/) c on b.objectProcessesDid = c.Did\n" +
//                "\t\t\t\t) d on b.processesDid = d.Did \n" +
//                "\t\t\t\twhere c.PeopleDid = subjectDid /*被试*/\n" +
//                "\t\t\t) a\n" +
//                "\t\t\tINNER JOIN\n" +
//                "\t\t\t(select a.Did Did,d.name name from \n" +
//                "\t\t\t\t`data` a \n" +
//                "\t\t\t\tINNER JOIN data_processes b on a.Did=b.DataDid\n" +
//                "\t\t\t\tINNER JOIN data_people c on c.DataDid = a.Did\n" +
//                "\t\t\t\tINNER JOIN\n" +
//                "\t\t\t\t(select a.Did Did,a.name name from /*找到所需所有trial*/\n" +
//                "\t\t\t\t\tProcesses a \n" +
//                "\t\t\t\t\tINNER JOIN processes_processes b on a.Did = b.SubjectProcessesDid\n" +
//                "\t\t\t\t\tINNER JOIN (select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid and a.name = blockname order by a.TimeStamp desc limit 1/*Block*/) c on b.objectProcessesDid = c.Did\n" +
//                "\t\t\t\t) d on b.processesDid = d.Did \n" +
//                "\t\t\t\twhere c.PeopleDid = teamDid /*赛队*/\n" +
//                "\t\t\t) b on a.Did = b.Did\n" +
//                "\t\t\tINNER JOIN data_trialresult c on c.DataDid=a.Did\n" +
//                "\t\t\tINNER JOIN trialresult d on d.Did = c.TrialResultDid;\n" +
//                "\t\t\t\n" +
//                "END;";
//        String sql5_0="DROP PROCEDURE IF EXISTS trialRightResult_subcompetition_block";
//        String sql5_1="CREATE PROCEDURE trialRightResult_subcompetition_block\n" +
//                "(\n" +
//                "    IN subcompetitionName CHAR(60),\n" +
//                "\tIN blockName CHAR(60)\n" +
//                ")\n" +
//                "BEGIN\n" +
//                "\t\tDECLARE subcompetitionDid INT;\n" +
//                "\t\t\n" +
//                "\t\tselect a.Did into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName; /*赛题*/\n" +
//                "\n" +
//                "\t\t\n" +
//                "\t\tselect c.name,a.result from\n" +
//                "\t\t\tTrialP a\n" +
//                "\t\t\tinner join Processes_TrialP b on a.Did=b.TrialPDid\n" +
//                "\t\t\tinner join\n" +
//                "\t\t\t(select a.Did Did,a.name name from /*找到所需所有trial*/\n" +
//                "\t\t\t\tProcesses a \n" +
//                "\t\t\t\tINNER JOIN processes_processes b on a.Did = b.SubjectProcessesDid\n" +
//                "\t\t\t\tINNER JOIN (select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid and a.name = blockname order by a.TimeStamp desc limit 1/*Block*/) c on b.objectProcessesDid = c.Did\n" +
//                "\t\t\t) c on c.Did=b.ProcessesDid;\n" +
//                "\t\t\n" +
//                "END;";
//        String sql6_0="DROP PROCEDURE IF EXISTS TeamsList";
//        String sql6_1="CREATE PROCEDURE TeamsList()\n" +
//                "BEGIN\n" +
//                "\t\tselect distinct TeamName from TeamP;\n" +
//                "\t\t\n" +
//                "END $$\n" +
//                "DELIMITER ;\n" +
//                "\n" +
//                "\n" +
//                "DELIMITER $$\n" +
//                "DROP PROCEDURE IF EXISTS TeamRank $$\n" +
//                "CREATE PROCEDURE TeamRank()\n" +
//                "BEGIN\n" +
//                "\tselect a.TeamName ,sum(e.result) score, rank() over(order by sum(e.result)) `rank` from\n" +
//                "\t\t(select a.TeamName, c.Did from\n" +
//                "\t\t\tTeamP a\n" +
//                "\t\t\tinner join People_TeamP b on a.Did=b.TeamPDid\n" +
//                "\t\t\tinner join people c on b.PeopleDid=c.Did) a\n" +
//                "\t\tinner join Data_People b on b.PeopleDid=a.Did\n" +
//                "\t\tinner join data c on c.Did=b.DataDid\n" +
//                "\t\tinner join data_blockresult d on c.Did = d.DataDid\n" +
//                "\t\tinner join blockresult e on d.BlockResultDid=e.Did\n" +
//                "\t\tgroup by a.TeamName ;\n" +
//                "\t\t\n" +
//                "END;";
//        String sql7_0="DROP PROCEDURE IF EXISTS TeamRank";
//        String sql7_1="CREATE PROCEDURE TeamRank()\n" +
//                "BEGIN\n" +
//                "\tselect a.TeamName ,sum(e.result) score, rank() over(order by sum(e.result)) `rank` from\n" +
//                "\t\t(select a.TeamName, c.Did from\n" +
//                "\t\t\tTeamP a\n" +
//                "\t\t\tinner join People_TeamP b on a.Did=b.TeamPDid\n" +
//                "\t\t\tinner join people c on b.PeopleDid=c.Did) a\n" +
//                "\t\tinner join Data_People b on b.PeopleDid=a.Did\n" +
//                "\t\tinner join data c on c.Did=b.DataDid\n" +
//                "\t\tinner join data_blockresult d on c.Did = d.DataDid\n" +
//                "\t\tinner join blockresult e on d.BlockResultDid=e.Did\n" +
//                "\t\tgroup by a.TeamName ;\n" +
//                "\t\t\n" +
//                "END;";
//        preparaedStatementUpdate.createProcess(sql1_0,sql1_1);
//
//
//    }
}
