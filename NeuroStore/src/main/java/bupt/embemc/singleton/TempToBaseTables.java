package bupt.embemc.singleton;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TempToBaseTables {
    public HashMap<String, HashMap> map;
    public void init (Map<String, HashMap> APImap,Map TempTables){
        map = new HashMap();

        HashMap<String,String> tempToMajorConf = new HashMap(APImap.get("APICLass"));
        for(String className : (Iterable<String>) TempTables.keySet()){
            String majorTable = tempToMajorConf.get(className);
            HashMap tempToMajor = new HashMap<>();
            if(majorTable == null){
                //TODO:添加请完善API映射表的异常
            }else{
                tempToMajor.put("PRIMARYTABLE",majorTable);
            }
            List<String> PropertiesTableList =new ArrayList<>();
            for(String s: (List<String>) APImap.get("APIType").get(className)){
                for(String s1 : (List<String>) APImap.get(majorTable + "Properties").get(s)){

                    PropertiesTableList.add(s1);


                }

            }
            tempToMajor.put("PROPERTIESTABLES",PropertiesTableList);
            map.put(className, tempToMajor);
        }

    }
}
