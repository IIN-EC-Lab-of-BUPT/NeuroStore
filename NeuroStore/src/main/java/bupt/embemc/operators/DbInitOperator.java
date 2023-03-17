package bupt.embemc.operators;

import bupt.embemc.singleton.DataTypeList;
import bupt.embemc.singleton.EntitiesMap;
import bupt.embemc.singleton.MajorToMajorTableName;
import bupt.embemc.singleton.TempToBaseTables;
import bupt.embemc.utils.DbOperator;
import bupt.embemc.utils.JSONReader;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import bupt.embemc.utils.JSONUtils;
import bupt.embemc.utils.PreparaedStatementUpdate;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component
public class DbInitOperator {
//    @Autowired
//    TempToBaseTables tempToBaseTables;
//
//    @Autowired
//    MajorToMajorTableName majorToMajorTableName;


    private TempToBaseTables tempToBaseTables;
    private  MajorToMajorTableName majorToMajorTableName;


    private HashMap entitiesMap = null;
    private HashMap dataTypeList = DataTypeList.getInstance().dataTypelist;

    public void  createTables(DbOperator DbO,PreparaedStatementUpdate ps,TempToBaseTables tempToBaseTables,MajorToMajorTableName majorToMajorTableName) throws SQLException {
        this.tempToBaseTables = tempToBaseTables;
        this.majorToMajorTableName = majorToMajorTableName;
        createTempTables(DbO, ps);
        DbO.commit(ps);
        log.info("transation commit!");
        createBaseTables(DbO,ps);
        DbO.commit(ps);
        log.info("transation commit!");
        initTables(DbO,ps);
        DbO.commit(ps);
        log.info("transation commit!");
//        initProcesses(DbO);
//        DbO.commit();
//        log.info("transation commit!");
        DbO.disconnect(ps);

    }


    private void createTempTables(DbOperator DbO,PreparaedStatementUpdate ps) throws SQLException {
        Map TempTables = JSONReader.getTempTables();

        Iterator<String> iterator = TempTables.keySet().iterator();
        while(iterator.hasNext()){
            String className = iterator.next();
            HashMap TempTableSql = new HashMap((Map)TempTables.get(className));
            HashMap TempTableSql1 = new HashMap();
            HashMap comment = new HashMap((Map)TempTableSql.get("COMMENT"));
            TempTableSql.remove("COMMENT");
            List<String> foreignKeyList = new ArrayList<>((List<String>)TempTableSql.get("FOREIGNKEYS"));
            TempTableSql.remove("FOREIGNKEYS");

            String primaryKey = (String) TempTableSql.get("PRIMARYKEY");
            TempTableSql1.put("PRIMARYKEY",primaryKey);

            foreignKeyList.add(primaryKey);

            for (String s: foreignKeyList
                 ) {
                String type = (String)TempTableSql.get(s);
                String commentStr = (String)comment.get(s);
                type = type + " COMMENT '"+commentStr+"'";
                TempTableSql1.put(s,type);
            }

            TempTableSql1.put(tempToBaseTables.map.get(className).get("PRIMARYTABLE"),"INT");
            List<String> propertiesTables = new ArrayList<>((List<String>) tempToBaseTables.map.get(className).get("PROPERTIESTABLES"));
            for (String s:propertiesTables
                 ) {
                TempTableSql1.put(s, "INT");
            }
            TempTableSql1.put("CLASS",className);
            TempTableSql1.put("CLASSCOMMENT",comment.get("CLASS"));


            DbO.create(TempTableSql1,ps);

        }
    }


    private void createBaseTables(DbOperator DbO,PreparaedStatementUpdate ps) throws SQLException {
        Map BaseTables = JSONReader.getBaseTables();

        Iterator<String> iterator = BaseTables.keySet().iterator();
        while(iterator.hasNext()){
            String className = iterator.next();
            HashMap BaseTableSql = new HashMap((Map)BaseTables.get(className));
            HashMap comment = new HashMap((Map)BaseTableSql.get("COMMENT"));
            BaseTableSql.remove("COMMENT");
            Iterator<String> iTable = BaseTableSql.keySet().iterator();
            String primaryKey = new String();
            while(iTable.hasNext()){
                String key = iTable.next();

                String type = (String)BaseTableSql.get(key);
                String commentStr = (String)comment.get(key);
                if(key.equals("Did")){
                    primaryKey = key;
                    type = type + " UNSIGNED AUTO_INCREMENT ";
                }
                type = type + " COMMENT '"+commentStr+"'";
                BaseTableSql.replace(key,type);

            }
            BaseTableSql.put("CLASS",className);
            BaseTableSql.put("PRIMARYKEY",primaryKey);
            BaseTableSql.put("CLASSCOMMENT",comment.get("CLASS"));
//            System.out.println(BaseTableSql);

            DbO.create(BaseTableSql,ps);
        }
    }


    private void initTables(DbOperator DbO,PreparaedStatementUpdate ps) throws SQLException {
        Map InitTable = JSONReader.getInitTables();

        for (String className : (Iterable<String>) InitTable.keySet()) {
            HashMap InitSql = new HashMap((Map) InitTable.get(className));
            InitSql.remove("Did#INT");
            Iterator<String> idata = InitSql.keySet().iterator();
            while (idata.hasNext()) {
                HashMap sql = new HashMap();
                String key = idata.next();
                sql.put("CLASS", className);
                sql.put("Did#INT", key);
                sql.put("type#VARCHAR(60)", InitSql.get(key));
//                System.out.println(sql);
                DbO.insert(sql,true,ps);

            }
        }
    }

    private void initProcesses(DbOperator DbO,PreparaedStatementUpdate ps) throws SQLException {
        String sql1_0 = "DROP PROCEDURE IF EXISTS score_team_subcompetition";
        String sql1_1 = "CREATE PROCEDURE score_team_subcompetition\n" +
                "(\n" +
                "    IN teamName CHAR(60),\n" +
                "    IN subcompetitionName CHAR(60)\n" +
                ")\n" +
                "BEGIN\n" +
                "    DECLARE subcompetition INT;\n" +
                "    DECLARE team INT;\n" +
                "    select a.Did into subcompetition from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
                "\n" +
                "    select a.Did into team from People a inner join People_TeamP b on a.Did=b.PeopleDid inner join TeamP c on b.TeamPDid = c.Did where c.teamname=teamname;\n" +
                "\t\n" +
                "    select sum(c.result)/count(c.result) score from\n" +
                "\t\t(select A.Did FROM \n" +
                "\t\t\t`data` a\n" +
                "\t\t\tINNER JOIN data_people b on a.Did=b.DataDid\n" +
                "\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did\n" +
                "\t\t\tINNER JOIN\t\n" +
                "\t\t\t(SELECT  DISTINCT (FIRST_VALUE(Did) over(partition by name order by TimeStamp desc ) )as Did from \n" +
                "\t\t\t\t(select a.Did Did,a.name name, a.TimeStamp timestamp from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetition) a \n" +
                "\t\t\t) t1 \n" +
                "\t\t\ton c.processesDid=t1.Did \n" +
                "\t\t\twhere b.PeopleDid=team\n" +
                "\t\t) a\t\t\t\t\t\t   \n" +
                "        INNER JOIN data_blockresult b on a.Did = b.DataDid\n" +
                "        INNER JOIN blockresult c on b.BlockresultDid = c.Did;\n" +
                "END;";
        DbO.createProcesses(ps,sql1_0,sql1_1);
        String sql2_0="DROP PROCEDURE IF EXISTS  score_subject_subcompetition ";
        String sql2_1="CREATE PROCEDURE  score_subject_subcompetition\n" +
                "(\n" +
                "    IN subjectID BIGINT,\n" +
                "    IN subcompetitionName CHAR(60)\n" +
                ")\n" +
                "BEGIN\n" +
                "    DECLARE subcompetitionDid INT;\n" +
                "    DECLARE subjectDid INT;\n" +
                "    select a.Did into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
                "\n" +
                "    select a.Did into subjectDid from People a inner join People_SubjectP b on a.Did=b.PeopleDid inner join SubjectP c on b.SubjectPDid = c.Did where c.ID=subjectID;\n" +
                "\t\n" +
                "\tselect sum(c.result)/count(c.result) score from \n" +
                "\t\t(select A.Did FROM \n" +
                "\t\t\t`data` a\n" +
                "\t\t\tINNER JOIN data_people b on a.Did=b.DataDid\n" +
                "\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did\n" +
                "\t\t\tINNER JOIN\t\n" +
                "\t\t\t(SELECT  DISTINCT (FIRST_VALUE(Did) over(partition by name order by TimeStamp desc ) )as Did from\n" +
                "\t\t\t\t(select a.Did Did,a.name name, a.TimeStamp timestamp from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid) a\n" +
                "\t\t\t) t1 \n" +
                "\t\t\ton c.processesDid=t1.Did \n" +
                "\t\t\twhere b.PeopleDid=subjectDid\n" +
                "\t\t) a\t\t\t\t\t\t   \n" +
                "        INNER JOIN data_blockresult b on a.Did = b.DataDid\n" +
                "        INNER JOIN blockresult c on b.BlockresultDid = c.Did;\n" +
                "    \n" +
                "END;";
        DbO.createProcesses(ps,sql2_0,sql2_1);
        String sql3_0="DROP PROCEDURE IF EXISTS score_subject_team_subcompetion";
        String sql3_1="CREATE PROCEDURE score_subject_team_subcompetion\n" +
                "(\n" +
                "    IN subjectID BIGINT,\n" +
                "\tIN teamName CHAR(60),\n" +
                "\tIN subcompetitionName CHAR(60)\n" +
                ")\n" +
                "BEGIN\n" +
                "    DECLARE subcompetitionDid INT;\n" +
                "\tDECLARE subjectDid INT;\n" +
                "\tDECLARE teamDid INT;\n" +
                "\tselect a.Did  into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
                "\n" +
                "\tselect a.Did into subjectDid from People a inner join People_SubjectP b on a.Did=b.PeopleDid inner join SubjectP c on b.SubjectPDid = c.Did where c.ID=subjectID;\n" +
                "\t\t\n" +
                "\tselect a.Did into teamDid from People a inner join People_TeamP b on a.Did=b.PeopleDid inner join TeamP c on b.TeamPDid = c.Did where c.teamname=teamName;\n" +
                "\t\t\n" +
                "\tselect a.name,c.result from\n" +
                "\t\t(select a.Did, a.name  from\n" +
                "\t\t\t(select a.Did Did, t1.name name FROM \n" +
                "\t\t\t\t`data` a \n" +
                "\t\t\t\tINNER JOIN data_people b on a.Did=b.DataDid \n" +
                "\t\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did \n" +
                "\t\t\t\tINNER JOIN \n" +
                "\t\t\t\t(SELECT  distinct name,(FIRST_VALUE(Did) over(partition by name order by TimeStamp desc) )as Did  from \n" +
                "\t\t\t\t\t(select a.Did Did,a.name name, a.TimeStamp timestamp from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid) a\n" +
                "\t\t\t\t) t1 on c.processesDid=t1.Did \n" +
                "\t\t\t\twhere b.PeopleDid=subjectDid\n" +
                "\t\t\t) a \n" +
                "\t\t\tINNER JOIN\n" +
                "\t\t\t(select a.Did Did FROM \n" +
                "\t\t\t\t`data` a \n" +
                "\t\t\t\tINNER JOIN data_people b on a.Did=b.DataDid \n" +
                "\t\t\t\tINNER JOIN data_processes c on c.DataDid=a.Did \n" +
                "\t\t\t\tINNER JOIN \n" +
                "\t\t\t\t(SELECT  distinct name,(FIRST_VALUE(Did) over(partition by name order by TimeStamp desc) )as Did  from\n" +
                "\t\t\t\t\t(select a.Did Did,a.name name, a.TimeStamp timestamp from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid) a \n" +
                "\t\t\t\t) t1 on c.processesDid=t1.Did \n" +
                "\t\t\t\twhere b.PeopleDid=teamDid\n" +
                "\t\t\t) b on a.Did = b.Did\n" +
                "\t\t) a \t\t\n" +
                "\t\tINNER JOIN data_blockresult b on a.Did = b.DataDid  \n" +
                "\t\tINNER JOIN blockresult c on b.BlockresultDid = c.Did;\n" +
                "END;";
        DbO.createProcesses(ps,sql3_0,sql3_1);
        String sql4_0="DROP PROCEDURE IF EXISTS trialDetials_subject_subcompetition_team_block";
        String sql4_1="CREATE PROCEDURE trialDetials_subject_subcompetition_team_block\n" +
                "(\n" +
                "    IN subjectID BIGINT,\n" +
                "    IN subcompetitionName CHAR(60),\n" +
                "\tIN teamName CHAR(60),\n" +
                "\tIN blockName CHAR(60)\n" +
                ")\n" +
                "BEGIN\n" +
                "    DECLARE subcompetitionDid INT;\n" +
                "\t\tDECLARE subjectDid INT;\n" +
                "\t\tDECLARE teamDid INT;\n" +
                "\t\t\n" +
                "\t\tselect a.Did into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
                "\n" +
                "\t\tselect a.Did into subjectDid from People a inner join People_SubjectP b on a.Did=b.PeopleDid inner join SubjectP c on b.SubjectPDid = c.Did where c.ID=subjectID;\n" +
                "\t\t\n" +
                "\t\tselect a.Did into teamDid from People a inner join People_TeamP b on a.Did=b.PeopleDid inner join TeamP c on b.TeamPDid = c.Did where c.teamname=teamName;\n" +
                "\t\t\n" +
                "\t\tselect b.name, d.score score,d.resultNum result, d.usedTime usedTime FROM\n" +
                "\t\t\t(select a.Did Did  from \n" +
                "\t\t\t\t`data` a \n" +
                "\t\t\t\tINNER JOIN data_processes b on a.Did=b.DataDid\n" +
                "\t\t\t\tINNER JOIN data_people c on c.DataDid = a.Did\n" +
                "\t\t\t\tINNER JOIN\n" +
                "\t\t\t\t(select a.Did Did from \n" +
                "\t\t\t\t\tProcesses a\n" +
                "\t\t\t\t\tINNER JOIN processes_processes b on a.Did = b.SubjectProcessesDid\n" +
                "\t\t\t\t\tINNER JOIN (select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid and a.name = blockname order by a.TimeStamp desc limit 1) c on b.objectProcessesDid = c.Did\n" +
                "\t\t\t\t) d on b.processesDid = d.Did \n" +
                "\t\t\t\twhere c.PeopleDid = subjectDid \n" +
                "\t\t\t) a\n" +
                "\t\t\tINNER JOIN\n" +
                "\t\t\t(select a.Did Did,d.name name from \n" +
                "\t\t\t\t`data` a \n" +
                "\t\t\t\tINNER JOIN data_processes b on a.Did=b.DataDid\n" +
                "\t\t\t\tINNER JOIN data_people c on c.DataDid = a.Did\n" +
                "\t\t\t\tINNER JOIN\n" +
                "\t\t\t\t(select a.Did Did,a.name name from \n" +
                "\t\t\t\t\tProcesses a \n" +
                "\t\t\t\t\tINNER JOIN processes_processes b on a.Did = b.SubjectProcessesDid\n" +
                "\t\t\t\t\tINNER JOIN (select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid and a.name = blockname order by a.TimeStamp desc limit 1) c on b.objectProcessesDid = c.Did\n" +
                "\t\t\t\t) d on b.processesDid = d.Did \n" +
                "\t\t\t\twhere c.PeopleDid = teamDid\n" +
                "\t\t\t) b on a.Did = b.Did\n" +
                "\t\t\tINNER JOIN data_trialresult c on c.DataDid=a.Did\n" +
                "\t\t\tINNER JOIN trialresult d on d.Did = c.TrialResultDid;\n" +
                "\t\t\t\n" +
                "END;";
        DbO.createProcesses(ps,sql4_0,sql4_1);
        String sql5_0="DROP PROCEDURE IF EXISTS trialRightResult_subcompetition_block";
        String sql5_1="CREATE PROCEDURE trialRightResult_subcompetition_block\n" +
                "(\n" +
                "    IN subcompetitionName CHAR(60),\n" +
                "\tIN blockName CHAR(60)\n" +
                ")\n" +
                "BEGIN\n" +
                "\t\tDECLARE subcompetitionDid INT;\n" +
                "\t\t\n" +
                "\t\tselect a.Did into subcompetitionDid from Processes a inner join Data_Processes b on a.Did = b.ProcessesDid inner join Data c on b.DataDid=c.Did where c.name=subcompetitionName;\n" +
                "\n" +
                "\t\t\n" +
                "\t\tselect c.name,a.result from\n" +
                "\t\t\tTrialP a\n" +
                "\t\t\tinner join Processes_TrialP b on a.Did=b.TrialPDid\n" +
                "\t\t\tinner join\n" +
                "\t\t\t(select a.Did Did,a.name name from\n" +
                "\t\t\t\tProcesses a \n" +
                "\t\t\t\tINNER JOIN processes_processes b on a.Did = b.SubjectProcessesDid\n" +
                "\t\t\t\tINNER JOIN (select a.Did Did from Processes a inner join Processes_Processes b on a.Did=b.subjectProcessesDid where b.ObjectProcessesDid = subcompetitionDid and a.name = blockname order by a.TimeStamp desc limit 1) c on b.objectProcessesDid = c.Did\n" +
                "\t\t\t) c on c.Did=b.ProcessesDid;\n" +
                "\t\t\n" +
                "END;";
        DbO.createProcesses(ps,sql5_0,sql5_1);
        String sql6_0="DROP PROCEDURE IF EXISTS TeamsList";
        String sql6_1="CREATE PROCEDURE TeamsList()\n" +
                "BEGIN\n" +
                "\t\tselect distinct TeamName from TeamP;\n" +
                "\t\t\n" +
                "END;\n";

        DbO.createProcesses(ps,sql6_0,sql6_1);
        String sql7_0="DROP PROCEDURE IF EXISTS TeamRank";
        String sql7_1="CREATE PROCEDURE TeamRank()\n" +
                "BEGIN\n" +
                "\tselect a.TeamName ,sum(e.result) score, rank() over(order by sum(e.result)) `rank` from\n" +
                "\t\t(select a.TeamName, c.Did from\n" +
                "\t\t\tTeamP a\n" +
                "\t\t\tinner join People_TeamP b on a.Did=b.TeamPDid\n" +
                "\t\t\tinner join people c on b.PeopleDid=c.Did) a\n" +
                "\t\tinner join Data_People b on b.PeopleDid=a.Did\n" +
                "\t\tinner join data c on c.Did=b.DataDid\n" +
                "\t\tinner join data_blockresult d on c.Did = d.DataDid\n" +
                "\t\tinner join blockresult e on d.BlockResultDid=e.Did\n" +
                "\t\tgroup by a.TeamName ;\n" +
                "\t\t\n" +
                "END;";
        DbO.createProcesses(ps,sql7_0,sql7_1);


    }

//    private void createProcess(){
//        String sql
//    }

    public void generateEntities(String jsonFile){

        try {
            PreparaedStatementUpdate preUpdate = new PreparaedStatementUpdate();
            String jsonString = JSONUtils.readJsonFile(jsonFile);
            JSONObject jsonObject = JSON.parseObject(jsonString, Feature.OrderedField);
            JSONArray jsonContent = jsonObject.getJSONArray("content");
            Iterator<Object> iterator = jsonContent.iterator();
            entitiesMap = EntitiesMap.getInstance().entities;

            while(iterator.hasNext()){
                JSONObject table = (JSONObject) iterator.next();
                String key = table.getString("CLASS");
                table.remove("CLASS");
                String value = JSON.toJSONString(table);
                entitiesMap.put(key,value);
                //key：CLAss，value：除去CLASS的其他json体
            }
        }catch (IOException e) {
            e = new IOException("Json file path configration error!");
            log.error(e.toString());
            System.exit(-1);
        }catch(RuntimeException r){
            r = new RuntimeException("DataBaseDefine.json format error!");
            log.error(r.toString());
            System.exit(-1);
        }
    }

    public ArrayList generateSqlstring(){
        String sqlS = null;
        String value_table_sql = null;
        ArrayList list = new ArrayList();
        Iterator iterator = entitiesMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String tableName = (String) entry.getKey();
            String value = (String) entry.getValue();
            JSONObject tableContent = JSON.parseObject(value,Feature.OrderedField);
            sqlS = "create table " + "`"+tableName+"`"+" (";
            Iterator iterator_table = tableContent.entrySet().iterator();
            while(iterator_table.hasNext()){
                Map.Entry entry_table = (Map.Entry)iterator_table.next();
                String key_table =(String) entry_table.getKey();
                String value_table =  entry_table.getValue().toString();
                if(dataTypeList.containsKey(value_table)){value_table_sql = (String) dataTypeList.get(value_table);}
                else{
                     switch(value_table.charAt(0)) {
                         case ('['): {
                             value_table_sql = "varchar(255)";
                             break;
                         }
                         case ('{'): {
                             value_table_sql = "varchar(255)";
                             break;
                         }
                         default:
                             IllegalStateException i = new IllegalStateException("Defined illegal data format!");
                             log.error(i.toString());
                             System.exit(0);
                     }
                }
                sqlS = sqlS + "`"+key_table+"`"+" " +value_table_sql+",";
            }
            sqlS = sqlS.substring(0,sqlS.length()-1);
            sqlS = sqlS + ");";
            log.info("SQL:"+sqlS);
            list.add(sqlS);
        }
        return list;
    }
}
