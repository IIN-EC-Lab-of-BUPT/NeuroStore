package bupt.embemc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonParser {
    public static Map<String,String> toMap(String content){
        JSONObject jsonObject = JSON.parseObject(content);


        HashMap<String, String> map = new HashMap();
        Iterator<String> iterator = jsonObject.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = jsonObject.getString(key);
            map.put(key, value);
        }

        return map;
    }
    public static String[] toList(String content){
        JSONArray jsonArray= JSON.parseArray(content);
        String[] contents = (String[]) jsonArray.toArray();


        return contents;
    }
}
