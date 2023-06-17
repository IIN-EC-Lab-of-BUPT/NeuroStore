package bupt.embemc.operators;

import bupt.embemc.operators.util.ABCCircle;
import bupt.embemc.singleton.MajorToMajorTableName;
import bupt.embemc.singleton.TempToBaseTables;
import bupt.embemc.utils.DbOperator;
import bupt.embemc.utils.JSONReader;
import bupt.embemc.utils.JsonParser;
import bupt.embemc.utils.PreparaedStatementUpdate;
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

    private String[] properties;
    private String[] relations;
    private HashMap<String, String> majorTableMap;



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
        String majorTable = map.get("MajorTable");
        majorTableMap = (HashMap<String, String>) JsonParser.toMap(majorTable);
        properties = JsonParser.toList(map.get("Properties"));
        relations = JsonParser.toList(map.get("Relation"));

        tableName = (String) majorTableMap.get("CLASS");
        String uuid = (String) majorTableMap.get("Did");

        String classUUID = tableName+uuid;

        if (majorTableMap.containsKey("content")) {
            Map value = lruCache.get(classUUID);
            String content = map.get("content");
            if(value != null){
                flag1=true;
                Queue q = (Queue) value.get("contentQ");
                q.add(content);
            }else{
                Dbtable = new HashMap((HashMap) TempTables.get(tableName));
                HashMap sql = new HashMap<>();
                sql.put("CLASS", tableName);
                sql.put(primaryKey + "#" + Dbtable.get("Did"), majorTableMap.get("Did"));
                resultSet = dbOperator.select(sql,ps);
                flag = resultSet.next();
                flag2=true;

                String filePath = "null";
                String fileType = majorTableMap.get("Format") != null ? (String)majorTableMap.get("Format") : "dat";
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
                        majorTableMap.put("Path", filePath_final);
                    }
                }else{
                    String filePath_final = toSave(content, fileType, filePath,classUUID);
                    majorTableMap.put("Path", filePath_final);
                }
            }
        }

    }
    @Override
    public void run(){
        HashMap exceptionMap = new HashMap<>(map);
        try {
            if(!flag1){
                HashMap<String ,String> majorTableSql = new HashMap<>();
                for(String s : majorTableMap.keySet()){
                    majorTableSql.put(s+"#CHAR(60)",majorTableMap.get(s));
                }
                dbOperator.insert(majorTableSql,ps);
                for(String s : properties){
                    HashMap<String,String> property = (HashMap<String, String>) JsonParser.toMap(s);
                    HashMap<String,String> property_sql = new HashMap<>();
                    for(String ss : property.keySet()){
                        property_sql.put(ss+"#CHAR(60)",property.get(ss));
                    }
                    dbOperator.insert(property_sql,ps);
                    HashMap<String,String> major_property = new HashMap<>();
                    major_property.put("CLASS",majorTableMap.get("CLASS")+"_"+property.get("CLASS"));
                    major_property.put(majorTableMap.get("CLASS")+"#CHAR(60)",majorTableMap.get("Did"));
                    major_property.put(property.get("CLASS")+"#CHAR(60)",property.get("Did"));
                    dbOperator.insert(major_property,ps);
                }
                for(String s : relations){
                    HashMap<String,String> relation = (HashMap<String, String>) JsonParser.toMap(s);
                    HashMap<String, String> relation_sql = new HashMap<>();

                    relation_sql.put("CLASS"+"#CHAR(60)",majorToMajorTableName.arrayMap[majorToMajorTableName.map.get(majorTableMap.get("CLASS"))][majorToMajorTableName.map.get(relation.get("CLASS"))]);
                    relation_sql.put(majorTableMap.get("CLASS")+"#CHAR(60)",majorTableMap.get("Did"));
                    relation_sql.put(relation.get("CLASS"),relation.get("Did"));
                    dbOperator.insert(relation_sql,ps);

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


}
