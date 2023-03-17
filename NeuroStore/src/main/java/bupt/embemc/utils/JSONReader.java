package bupt.embemc.utils;

import bupt.embemc.singleton.EntitiesMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class JSONReader {


    private JSONReader() {

    }

    /*
    {"APICLass":HashMap<String, String>,
    "APIType":HashMap<String, List<String>>,
    "DevicesProperties":HashMap<String, List<String>>,
    "PeopleProperties":HashMap<String, List<String>>,
    "ParadigmsProperties":HashMap<String, List<String>>,
     "DataProperties":HashMap<String, List<String>>,
     "ProcessesProperties":HashMap<String, List<String>>};
     */
    private final static Map APImap = new HashMap();
    /*
        {"
        CLASSName":HashMap<String,String>(no "CLASS",primarykey have no *),
                                HashMap has "COMMENT":HashMap<String,String>(every field comment,no "CLASS")
                                             and "PRIMARYKEY":"key no *"
                                             and "FOREIGNKEYS"
        }
     */
    private final static Map TempTables = new HashMap<>();
    /*
        {
        "CLASSName":HashMap<String,String>(no "CLASS"),
                               HashMap has "COMMENT":HashMap<String,String>(every field comment,no "CLASS")
        }
     */
    private final static Map BaseTables = new HashMap<>();
    /*
    {                                                                              {
                                                                                    "Did#INT": "type#VARCHAR(60)",
    "CLASSName":HashMap<String, String>            HashMap<String, String> ——>      "num":"content"
                                                                                   {
    }

     */
    private final static Map InitTables = new HashMap<>();

    public static Map getAPIMap() {
        return APImap;
    }

    public static Map getTempTables() {
        return TempTables;
    }

    public static Map getBaseTables() {
        return BaseTables;
    }

    public static Map getInitTables() {
        return InitTables;
    }


    public static void init(String jsonFile, String confPath) {
        readDataBaseDefine(jsonFile);
        readAPIConfig(confPath);

    }

    public static void readDataBaseDefine(String jsonFile) {
        String jsonString = null;
        try {
            jsonString = JSONUtils.readJsonFile(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(jsonString, Feature.OrderedField);
        JSONArray jsonContent = jsonObject.getJSONArray("TempTables");
        JSONArray jsonContent1 = jsonObject.getJSONArray("BaseTables");
        JSONArray jsonContent2 = jsonObject.getJSONArray("InitTables");
        Object[] objects = jsonContent.toArray();
        Object[] objects1 = jsonContent1.toArray();
        Object[] objects2 = jsonContent2.toArray();
        for (Object ob : objects
        ) {
            JSONObject job = JSONObject.parseObject(JSON.toJSONString(ob), Feature.OrderedField);
            String className = job.getString("CLASS");
            job.remove("CLASS");
            JSONObject comment = JSONObject.parseObject(job.getString("COMMENT"), Feature.OrderedField);
            job.remove("COMMENT");
            Iterator<String> iterator = comment.keySet().iterator();
            Map commentMap = new HashMap();

            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = comment.getString(key);
                if(key.charAt(0) == '*'){
                    key = key.substring(1);
                }
                commentMap.put(key, value);
            }
            Map tableContentMap = new HashMap();
            Iterator<String> iterator1 = job.keySet().iterator();
            List<String> foreignKeyList = new ArrayList<>();
            while (iterator1.hasNext()) {
                String key = iterator1.next();
                String value = job.getString(key);
                if(key.charAt(0) == '*'){
                    key = key.substring(1);
                    tableContentMap.put("PRIMARYKEY", key);
                }
                if(key.contains("@")){

                    foreignKeyList.add(key);
                }
                tableContentMap.put(key, value);
            }
            tableContentMap.put("FOREIGNKEYS",foreignKeyList);
            tableContentMap.put("COMMENT", commentMap);
            TempTables.put(className, tableContentMap);

        }
        for (Object ob : objects1
        ) {
            JSONObject job = JSONObject.parseObject(JSON.toJSONString(ob), Feature.OrderedField);
            String className = job.getString("CLASS");
            job.remove("CLASS");
            JSONObject comment = JSONObject.parseObject(job.getString("COMMENT"), Feature.OrderedField);
            job.remove("COMMENT");
            Iterator<String> iterator = comment.keySet().iterator();
            Map commentMap = new HashMap();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = comment.getString(key);
                commentMap.put(key, value);
            }
            Map tableContentMap = new HashMap();
            Iterator<String> iterator1 = job.keySet().iterator();
            while (iterator1.hasNext()) {
                String key = iterator1.next();
                String value = job.getString(key);
                tableContentMap.put(key, value);
            }
            tableContentMap.put("COMMENT", commentMap);
            BaseTables.put(className, tableContentMap);

        }
        for (Object ob : objects2
        ) {
            JSONObject job = JSONObject.parseObject(JSON.toJSONString(ob), Feature.OrderedField);
            String className = job.getString("CLASS");
            job.remove("CLASS");
            Map tableContentMap = new HashMap();
            Iterator<String> iterator1 = job.keySet().iterator();
            while (iterator1.hasNext()) {
                String key = iterator1.next();
                String value = job.getString(key);
                tableContentMap.put(key, value);
            }
            InitTables.put(className, tableContentMap);

        }

    }

    public static void readAPIConfig(String confPath) {
        String jsonString = null;
        try {
            jsonString = JSONUtils.readJsonFile(confPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(jsonString, Feature.OrderedField);
        JSONObject jsonObject1 = jsonObject.getJSONObject("APICLass");

        Map APICLassMap = new HashMap();

        Iterator<String> iterator = jsonObject1.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = jsonObject1.getString(key);
            APICLassMap.put(key, value);
        }
        APImap.put("APICLass", APICLassMap);

        String[] target = new String[]{"APIType", "DevicesProperties", "PeopleProperties", "ParadigmsProperties", "DataProperties", "ProcessesProperties"};
        for (String s : target
        ) {
            JSONObject jsonObject_temp = jsonObject.getJSONObject(s);
            Map map_temp = new HashMap();
            Iterator<String> iterator1_temp = jsonObject_temp.keySet().iterator();
            while (iterator1_temp.hasNext()) {
                String key = iterator1_temp.next();
                JSONArray jsonArray = jsonObject_temp.getJSONArray(key);
                List<String> value = new ArrayList<>();
                Iterator jsonArrayiterator1 = jsonArray.iterator();
                while (jsonArrayiterator1.hasNext()) {
                    value.add((String) jsonArrayiterator1.next());
                }
                map_temp.put(key, value);
            }
            APImap.put(s, map_temp);
        }


    }
}
