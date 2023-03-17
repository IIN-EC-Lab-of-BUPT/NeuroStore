package bupt.embemc.singleton;

import bupt.embemc.utils.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.io.IOException;
import java.util.*;

public class APIConfigMap {

    public static Map getMap() {
        return map;
    }

    private static Map map = new HashMap();

    public static void init(String confPath){

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
        while(iterator.hasNext()){
            String key = iterator.next();
            String value = jsonObject1.getString(key);
            APICLassMap.put(key,value);
        }
        map.put("APICLass",APICLassMap);

        String[] target = new String[]{"APIType","DevicesProperties","PeopeleProperties","ParadigmsProperties","DataProperties","ProcessesProperties"};
        for (String s:target
             ) {
            JSONObject jsonObject_temp  = jsonObject.getJSONObject(s);
            Map map_temp = new HashMap();
            Iterator<String> iterator1_temp= jsonObject_temp.keySet().iterator();
            while(iterator1_temp.hasNext()){
                String key = iterator1_temp.next();
                JSONArray jsonArray = jsonObject_temp.getJSONArray(key);
                List<String> value = new ArrayList<>();
                Iterator jsonArrayiterator1= jsonArray.iterator();
                while(jsonArrayiterator1.hasNext()){
                    value.add((String)jsonArrayiterator1.next());
                }
                map_temp.put(key,value);
            }
            map.put(s,map_temp);

        }
    }
}
