package bupt.embemc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Author:Jack Chen
 * 2021/12/3
 * description:
 */

public class JSONUtils {

    //从文件中读取json对象
    public static String readJsonFile(String fileName) throws IOException {
        String jsonStr = "";

        File jsonFile = new File(fileName);
        FileReader fileReader = new FileReader(jsonFile);
        Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        jsonStr = sb.toString();
        return jsonStr;

    }

    //将字符串键值对JSONObject对象转成HashMap对象
//    public static HashMap<String, String> JsonObjectToHashMap(JSONObject jsonObj){
//        HashMap<String, String> data = new HashMap<String, String>();
//        Iterator it = jsonObj.keys();
//        while(it.hasNext()){
//            String key = String.valueOf(it.next().toString());
//            String value = (String)jsonObj.get(key).toString();
//            data.put(key, value);
//        }
////        System.out.println(data);
//        return data;
//    }
}
