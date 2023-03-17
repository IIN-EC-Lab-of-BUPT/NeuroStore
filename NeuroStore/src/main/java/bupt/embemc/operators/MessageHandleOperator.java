package bupt.embemc.operators;

import bupt.embemc.operators.util.ABCCircle;
import bupt.embemc.singleton.EntitiesMap;
import bupt.embemc.singleton.MajorToMajorTableName;
import bupt.embemc.singleton.TempToBaseTables;
import bupt.embemc.utils.DbOperator;
import bupt.embemc.utils.JSONReader;
import bupt.embemc.utils.PreparaedStatementUpdate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import bupt.embemc.operators.util.LRUCache;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class MessageHandleOperator implements Runnable {

    private static String pathPrefix;
    private int i = 0;

    //@Autowired//TODO:为什么注入失效,可能跟。connect9()有关
    PreparaedStatementUpdate ps;

    PreparaedStatementUpdate ps1;

    DbOperator dbOperator;

    static LRUCache lruCache;

//    @Autowired
    static TempToBaseTables tempToBaseTables;

    static ABCCircle abcCircle;

    //@Autowired
    static MajorToMajorTableName majorToMajorTableName;

    private   Map<String,HashMap> APImap = JSONReader.getAPIMap();
    private   Map<String,HashMap> TempTables = JSONReader.getTempTables();
    private   Map BaseTables = JSONReader.getBaseTables();

    private HashMap<String,String> map;
    private String tableName;
    private HashMap Dbtable;
    private String primaryKey;
    private boolean flag=false;
    private boolean flag1=false;
    private boolean flag2=false;
    private ResultSet resultSet;




    public MessageHandleOperator(HashMap<String,String> inMap,DbOperator dbOperator) throws SQLException, IOException, ClassNotFoundException {
        this.dbOperator = dbOperator;
        this.ps = new PreparaedStatementUpdate();
        this.ps1 = new PreparaedStatementUpdate();
        dbOperator.connect(1,ps);
        dbOperator.connect(1,ps1);
        map=inMap;



    }
    public static void init(TempToBaseTables tempToBaseTables,MajorToMajorTableName majorToMajorTableName,String pathPrefix,LRUCache lruCache,ABCCircle abcCircle){

        MessageHandleOperator.tempToBaseTables = tempToBaseTables;
        MessageHandleOperator.majorToMajorTableName = majorToMajorTableName;
        MessageHandleOperator.pathPrefix = pathPrefix;
        MessageHandleOperator.lruCache = lruCache;
        MessageHandleOperator.abcCircle = abcCircle;

    }


    private String toSave(String content,String fileType,String filePath,String classUUID) throws Exception {
        String abstractPath = filePath;
        if(filePath.equals("null")){
            String pathPrefix;
            if(fileType.equals("dat")){
                pathPrefix = MessageHandleOperator.pathPrefix + "/data";
            }else if(fileType.equals("jpg")||fileType.equals("png")){
                pathPrefix = MessageHandleOperator.pathPrefix + "/pictures";
            }else if(fileType.equals("mp4")||fileType.equals("wav")){
                pathPrefix = MessageHandleOperator.pathPrefix +"/videos";
            }
            else{
                pathPrefix =MessageHandleOperator.pathPrefix + "/others";
            }
            char alphabet = abcCircle.getAlphabet();
            String fileName = Long.valueOf(System.currentTimeMillis()).toString()+alphabet;
            abstractPath = Paths.get(pathPrefix , fileName +"."+ fileType).toString();
//            log.info("====cy===="+fileName);
        }
        abstractPath = abstractPath.replace("\\","\\\\");
        String[] fileContent = new String[]{content,abstractPath};

        Map value = new HashMap();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Queue q =new ConcurrentLinkedQueue();
        q.add(content);
        value.put("filePath",abstractPath);
//        value.put("reading",atomicBoolean);
        value.put("contentQ",q);
        lruCache.put(classUUID,value);
        return abstractPath;
    }
    public void proprecess() throws Exception,SQLException{
        tableName = map.get("CLASS");
        primaryKey = new String();
        map.remove("CLASS");
        String ss = new String();
        int primaryKeyNum = 0;
        for (String s : map.keySet()) {
            if (s.charAt(0) == '*') {
                primaryKey = s.substring(1);
                ss = s;
                primaryKeyNum++;
                break;
            }
        }
        if(primaryKeyNum != 1){
            throw new Exception("PrimaryKey Wrong!");
        }
        String uuid = map.get(ss);
        map.put(primaryKey, uuid);
        map.remove(ss);
        String classUUID = tableName+uuid;


        if (map.containsKey("content")) {
            Map value = lruCache.get(classUUID);
            String content = map.get("content");
            if(value != null){
                flag1=true;
                Queue q = (Queue) value.get("contentQ");
                q.add(content);
            }else{
                Dbtable = new HashMap((HashMap) TempTables.get(tableName));


//                List<String> foreignKeys = new ArrayList<String>((List<String>) Dbtable.get("FOREIGNKEYS"));
//                String lockSql="lock tables";
//                lockSql = lockSql + "`"+tableName+"`"+" write";
//                for(String forignKey:foreignKeys){
//                    String foreiTable = forignKey.split("@")[1];
//                    lockSql = lockSql + ",`"+foreiTable+"`"+" write";
//                }
//                lockSql += ";";
//                DbO.excuteSql(lockSql);



                HashMap sql = new HashMap<>();
                sql.put("CLASS", tableName);
                sql.put(primaryKey + "#" + Dbtable.get(primaryKey), map.get(primaryKey));
                resultSet = dbOperator.select(sql,ps);
                flag = resultSet.next();
                flag2=true;



                String filePath = "null";
                String fileType = map.get("Format") != null ? map.get("Format") : "dat";
                if (flag) {
                    Integer dataFilesDid = resultSet.getInt("DataFiles");
                    HashMap query_datafiles = new HashMap();
                    query_datafiles.put("CLASS", "DataFiles");
                    query_datafiles.put("Did#INT", dataFilesDid.toString());
                    ResultSet dataFilesResult = dbOperator.select(query_datafiles,ps1);
                    if (dataFilesResult.next() && dataFilesResult.getString("Path") != null) {
                        filePath = dataFilesResult.getString("Path");
                        toSave(content, fileType, filePath,classUUID);
                    }else{
                        String filePath_final = toSave(content, fileType, filePath,classUUID);
                        map.put("Path", filePath_final);
                    }
                }else{
                    String filePath_final = toSave(content, fileType, filePath,classUUID);
                    map.put("Path", filePath_final);
                }
            }
            map.remove("content");
            if(map.size() < 2){
                flag1=true;
            }
        }

    }
    @Override
    public void run(){
        HashMap exceptionMap = new HashMap<>(map);
        try {
            if(!flag1){
                if(!flag2){
                    Dbtable = new HashMap((HashMap) TempTables.get(tableName));


                    List<String> foreignKeys = new ArrayList<String>((List<String>) Dbtable.get("FOREIGNKEYS"));


//                    String lockSql="lock tables";
//                    lockSql = lockSql + "`"+tableName+"`"+" write";
//                    for(String forignKey:foreignKeys){
//                        String foreiTable = forignKey.split("@")[1];
//                        lockSql = lockSql + ",`"+foreiTable+"`"+" write";
//                    }
//                    lockSql += ";";
//                    DbO.excuteSql(lockSql);


                    HashMap sql = new HashMap<>();
                    sql.put("CLASS", tableName);
                    sql.put(primaryKey + "#" + Dbtable.get(primaryKey), map.get(primaryKey));
                    resultSet = dbOperator.select(sql,ps);
                    flag=resultSet.next();
                }
                if(flag){
                    List<String> foreignKeys = new ArrayList<String>((List<String>) Dbtable.get("FOREIGNKEYS"));

                    HashMap<String ,String> majorTales_foreignKey = new HashMap<>();
                    for(String foreignKey: foreignKeys){
                        if(map.get(foreignKey)!= null){
                            if(map.get(foreignKey).equals(resultSet.getString(foreignKey))){

                            }else{
                                String foreignKeyTableName = foreignKey.split("@")[1];
                                String foreignTablePrimaryKey = foreignKey.split("@")[0];

                                HashMap sql_ForeignTable = new HashMap<>();
                                sql_ForeignTable.put("CLASS",foreignKeyTableName);
                                sql_ForeignTable.put(foreignTablePrimaryKey+"#"+Dbtable.get(foreignKey),map.get(foreignKey));
                                ResultSet resultSet_ForeignTableQuery = dbOperator.select(sql_ForeignTable,ps);
                                if(resultSet_ForeignTableQuery.next()){
                                    Integer majorTableDid_foreignTable = resultSet_ForeignTableQuery.getInt((String) tempToBaseTables.map.get(foreignKeyTableName).get("PRIMARYTABLE"));

                                    String primaryTable_foreignKey = (String) tempToBaseTables.map.get(foreignKeyTableName).get("PRIMARYTABLE");
                                    if(majorTales_foreignKey.containsKey(primaryTable_foreignKey)){
                                        primaryTable_foreignKey += "&*";
                                    }
                                    majorTales_foreignKey.put(primaryTable_foreignKey,majorTableDid_foreignTable.toString());
                                    //update
                                }else{
                                    //insert
                                    HashMap<String,String> dataMap = new HashMap<>();
                                    dataMap.put(foreignTablePrimaryKey,map.get(foreignKey));
                                    HashMap<String ,String> majorTaleAndDidKey_foreignKey = new HashMap<>();
                                    Integer primaryKey_foreign=this.insert(foreignKeyTableName,dataMap,majorTaleAndDidKey_foreignKey);

                                    String primaryTable_foreignKey = (String) tempToBaseTables.map.get(foreignKeyTableName).get("PRIMARYTABLE");
                                    if(majorTales_foreignKey.containsKey(primaryTable_foreignKey)){
                                        primaryTable_foreignKey += "&*";
                                    }
                                    majorTales_foreignKey.put(primaryTable_foreignKey,primaryKey_foreign.toString());

                                }
                            }
                        }
                    }
                    HashMap<String,String> baseTables_tableNameAndDid = new HashMap<>();
                    HashMap temptobase = tempToBaseTables.map.get(tableName);
                    String majorTableName = (String) temptobase.get("PRIMARYTABLE");
                    List<String> propertiestables =new ArrayList<>((List<String>) temptobase.get("PROPERTIESTABLES"));
                    propertiestables.add(majorTableName);
                    for(String s: propertiestables){
                        baseTables_tableNameAndDid.put(s,Integer.valueOf(resultSet.getInt(s)).toString());
                    }

                    this.update(tableName,map,majorTales_foreignKey,baseTables_tableNameAndDid);
                    //update(String tableName, HashMap<String,String> dataMap,HashMap<String ,String> majorTaleAndDidKey_foreignKey,HashMap<String,String> baseTables_tableNameAndDid)


                }else{
                    List<String> foreignKeys = new ArrayList<String>((List<String>) Dbtable.get("FOREIGNKEYS"));
                    HashMap<String ,String> majorTales_foreignKey = new HashMap<>();
                    for(String foreignKey: foreignKeys){
                        if(map.get(foreignKey) != null){
                            String foreignKeyTableName = foreignKey.split("@")[1];
                            String foreignTablePrimaryKey = foreignKey.split("@")[0];

                            HashMap sql_ForeignTable = new HashMap<>();
                            sql_ForeignTable.put("CLASS",foreignKeyTableName);
                            sql_ForeignTable.put(foreignTablePrimaryKey+"#"+Dbtable.get(foreignKey),map.get(foreignKey));
                            ResultSet resultSet_ForeignTableQuery = dbOperator.select(sql_ForeignTable,ps);
                            if(resultSet_ForeignTableQuery.next()){
                                Integer majorTableDid_foreignTable = resultSet_ForeignTableQuery.getInt((String) tempToBaseTables.map.get(foreignKeyTableName).get("PRIMARYTABLE"));
                                String primaryTable_foreignKey = (String) tempToBaseTables.map.get(foreignKeyTableName).get("PRIMARYTABLE");
                                if(majorTales_foreignKey.containsKey(primaryTable_foreignKey)){
                                    primaryTable_foreignKey += "&*";
                                }
                                majorTales_foreignKey.put(primaryTable_foreignKey,majorTableDid_foreignTable.toString());
                                //update
                            }else{
                                //insert
                                HashMap<String,String> dataMap = new HashMap<>();
                                dataMap.put(foreignTablePrimaryKey,map.get(foreignKey));
                                HashMap<String ,String> majorTaleAndDidKey_foreignKey = new HashMap<>();
                                Integer primaryKey_foreign=this.insert(foreignKeyTableName,dataMap,majorTaleAndDidKey_foreignKey);
                                String primaryTable_foreignKey = (String) tempToBaseTables.map.get(foreignKeyTableName).get("PRIMARYTABLE");
                                if(majorTales_foreignKey.containsKey(primaryTable_foreignKey)){
                                    primaryTable_foreignKey += "&*";
                                }
                                majorTales_foreignKey.put(primaryTable_foreignKey,primaryKey_foreign.toString());

                            }
                        }
                    }

                    //insert
                    this.insert(tableName,map,majorTales_foreignKey);
                }
            }


            dbOperator.commit(ps);
//            log.info("DbO commit!");
            dbOperator.commit(ps1);
//            log.info("DbO1 commit!");

        }catch (Exception e ){
            boolean flag = true;
//            System.out.println(Thread.currentThread());
//            e.printStackTrace();
            try{
                dbOperator.rollback(ps);
                dbOperator.rollback(ps1);
            }catch (SQLException sqlException){
                flag = false;
            }
            dbOperator.disconnect(ps);
            dbOperator.disconnect(ps1);
            String error =  "MessageError Or ConfigError!! ";
            if(flag){
                error += "Transaction rollback succeed!";
            }
            else{
                error += "Transaction rollback failed!";
            }
            error += "{";
            if(exceptionMap.get("CLASS") != null){
                error += "CLASS:"+exceptionMap.get("CLASS");
                if(exceptionMap.get("*"+TempTables.get(exceptionMap.get("CLASS")).get("PRIMARYKEY")) != null){
                    error = error+"," + TempTables.get(exceptionMap.get("CLASS")).get("PRIMARYKEY") + ":" + exceptionMap.get("*"+TempTables.get(exceptionMap.get("CLASS")).get("PRIMARYKEY"));
                }
            }

            error +="} Detail:";
            log.error(error+e.toString());

        }finally {
            try{
//                DbO.excuteSql("unlock tables;");
//                DbO1.excuteSql("unlock tables;");
//                log.info("unlock tables:"+Thread.currentThread());
            }catch (Exception e){
//                log.warn("unlock tables:"+Thread.currentThread()+"fail!");
            }
            dbOperator.disconnect(ps);
            dbOperator.disconnect(ps1);
        }
//        i++;
//        log.info("done"+i);

    }
    //dataMap 是原版数据，主键带* //tableName:要更新temp表名；  dataMap：消息体，没有“CLASS”，没有 * ；  majorTaleAndDidKey_foreignKey：外键表的主表名和Did；
    private int insert(String tableName, HashMap<String,String> dataMap,HashMap<String ,String> majorTaleAndDidKey_foreignKey) throws SQLException{
        String majorTableName = (String)tempToBaseTables.map.get(tableName).get("PRIMARYTABLE");

        HashMap sql_tempTable = new HashMap();
        sql_tempTable.put("CLASS",tableName);

        HashMap tempTable = new HashMap((HashMap) TempTables.get(tableName));

        String primaryKey = (String) tempTable.get("PRIMARYKEY");
        List<String> foreignKeys = new ArrayList<String>((List<String>) tempTable.get("FOREIGNKEYS"));
        sql_tempTable.put(primaryKey+"#"+tempTable.get(primaryKey), dataMap.get(primaryKey));
        for(String s: foreignKeys){
            if(dataMap.get(s) != null){
                sql_tempTable.put(s+"#"+tempTable.get(s),dataMap.get(s));
            }
        }


        HashMap<String, String> baseTable = new HashMap((HashMap)BaseTables.get(majorTableName));
        baseTable.remove("COMMENT");
        HashMap sql_majorTable = new HashMap();
        sql_majorTable.put("CLASS",majorTableName);
        for(String s:  baseTable.keySet()){
            String dataValue = dataMap.get(s);
            if(dataValue != null){
                sql_majorTable.put(s+"#"+baseTable.get(s), dataValue);
            }
        }
        //majorTable
        Integer primaryKey_majorTable = dbOperator.insert(sql_majorTable,ps1);
        sql_tempTable.put(majorTableName+"#"+"INT", primaryKey_majorTable.toString());

        List<String> propertiesTables = new ArrayList<>((List<String>) tempToBaseTables.map.get(tableName).get("PROPERTIESTABLES"));
        for(String s : propertiesTables){
            HashMap<String,String> baseTable_temp = new HashMap((HashMap) BaseTables.get(s));
            baseTable_temp.remove("COMMENT");
            HashMap sql_propertiesTable = new HashMap();
            sql_propertiesTable.put("CLASS",s);
            for(String s1 : baseTable_temp.keySet()){
                String dateValue_temp = dataMap.get(s1);
                if(dateValue_temp != null){
                    sql_propertiesTable.put(s1+"#"+baseTable_temp.get(s1), dateValue_temp);
                }
            }
            //PropertiesTables
            Integer primaryKey_propertiesTable = dbOperator.insert(sql_propertiesTable,ps1);
            sql_tempTable.put(s+"#"+"INT", primaryKey_propertiesTable.toString());

            HashMap<String,String> sql_majorPropertiesRelation = new HashMap();
            sql_majorPropertiesRelation.put("CLASS",majorTableName+"_"+s);
            sql_majorPropertiesRelation.put(majorTableName+"Did#INT",primaryKey_majorTable.toString());
            sql_majorPropertiesRelation.put(s+"Did#INT",primaryKey_propertiesTable.toString());

            //major to perpertiesTables relation
            dbOperator.insert(sql_majorPropertiesRelation,ps1);
        }

        //tempTable
        dbOperator.insert(sql_tempTable,ps);

        List<String> types = new ArrayList<>((List<String>) APImap.get("APIType").get(tableName));
        for(String s1: types){
            HashMap sql_majorToType = new HashMap();
            sql_majorToType.put("CLASS", majorTableName+"_"+majorTableName+"Types");
            sql_majorToType.put(majorTableName+"Did#INT",primaryKey_majorTable.toString());
            sql_majorToType.put(majorTableName+"TypesDid#INT",s1);

            //major to types relation
            dbOperator.insert(sql_majorToType,ps1);
        }
        for(String s:majorTaleAndDidKey_foreignKey.keySet()){
            HashMap sql_majorToMajorRelation = new HashMap();
            String primaryKey_foreignTable = majorTaleAndDidKey_foreignKey.get(s);
            s = s.split("&")[0];
            sql_majorToMajorRelation.put("CLASS",majorToMajorTableName.arrayMap[majorToMajorTableName.map.get(majorTableName)][majorToMajorTableName.map.get(s)]);
            if(s.equals(majorTableName)){
                sql_majorToMajorRelation.put("Subject"+majorTableName+"Did#INT", primaryKey_majorTable.toString());
                sql_majorToMajorRelation.put("Object"+s+"Did#INT",primaryKey_foreignTable);
            }else{
                sql_majorToMajorRelation.put(majorTableName+"Did#INT", primaryKey_majorTable.toString());
                sql_majorToMajorRelation.put(s+"Did#INT",primaryKey_foreignTable);
            }
            //major to major relation
            dbOperator.insert(sql_majorToMajorRelation,ps1);
        }
        return primaryKey_majorTable;
    }
    //tableName:要更新temp表名；  dataMap：消息体，没有“CLASS”，没有 * ；  majorTaleAndDidKey_foreignKey：外键表的主表名和Did； baseTables_tableNameAndDid：基础表的tableName和Did
    private void update(String tableName, HashMap<String,String> dataMap,HashMap<String ,String> majorTaleAndDidKey_foreignKey,HashMap<String,String> baseTables_tableNameAndDid) throws SQLException{
        HashMap sql_tempTable = new HashMap();
        sql_tempTable.put("CLASS",tableName);
        HashMap tempTable = new HashMap((HashMap) TempTables.get(tableName));
        String primaryKey = (String) tempTable.get("PRIMARYKEY");
        String[][] condition = {{primaryKey+"#"+tempTable.get(primaryKey),"=",dataMap.get(primaryKey)}};
        sql_tempTable.put("CONDITION",condition);

        List<String> foreignKeys = new ArrayList<>((List<String>) tempTable.get("FOREIGNKEYS"));
        sql_tempTable.put(primaryKey+"#"+tempTable.get(primaryKey), dataMap.get(primaryKey));
        for(String s: foreignKeys){
            if(dataMap.get(s) != null){
                sql_tempTable.put(s+"#"+tempTable.get(s),dataMap.get(s));
            }
        }
        dbOperator.update(sql_tempTable,ps);

        String majorTableName = (String)tempToBaseTables.map.get(tableName).get("PRIMARYTABLE");
        for(String s:majorTaleAndDidKey_foreignKey.keySet()) {
            String primaryKey_foreignTable = majorTaleAndDidKey_foreignKey.get(s);
            s = s.split("&")[0];
            HashMap sql_majorToMajorRelation = new HashMap();
            sql_majorToMajorRelation.put("CLASS", majorToMajorTableName.arrayMap[majorToMajorTableName.map.get(majorTableName)][majorToMajorTableName.map.get(s)]);
            if(s.equals(majorTableName)){
                sql_majorToMajorRelation.put("Subject"+majorTableName+"Did#INT", baseTables_tableNameAndDid.get(majorTableName));
                sql_majorToMajorRelation.put("Object"+s+"Did#INT",primaryKey_foreignTable);
            }else{
                sql_majorToMajorRelation.put(majorTableName+"Did#INT", baseTables_tableNameAndDid.get(majorTableName));
                sql_majorToMajorRelation.put(s+"Did#INT",primaryKey_foreignTable);
            }

            //major to major relation
            dbOperator.insert(sql_majorToMajorRelation,ps1);
        }

        for(String s: baseTables_tableNameAndDid.keySet()){
            HashMap sql_baseTables = new HashMap();
            sql_baseTables.put("CLASS",s);
            String[][] condition_baseTables = {{"Did#INT","=",baseTables_tableNameAndDid.get(s)}};
            sql_baseTables.put("CONDITION",condition_baseTables);
            HashMap<String,String> baseTable = new HashMap((HashMap) BaseTables.get(s));
            baseTable.remove("COMMENT");
            for(String s1: baseTable.keySet()){
                if(dataMap.get(s1) != null){
                    sql_baseTables.put(s1+"#"+baseTable.get(s1),dataMap.get(s1));
                }
            }
            dbOperator.update(sql_baseTables,ps1);
        }
    }


}
