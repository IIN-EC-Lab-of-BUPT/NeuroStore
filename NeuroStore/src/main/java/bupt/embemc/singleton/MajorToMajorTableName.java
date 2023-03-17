package bupt.embemc.singleton;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MajorToMajorTableName {
    public Map<String, Integer> map = new HashMap<>();
    public String[][] arrayMap = new String[][]{
            {"Data_Data","Data_Devices","Data_Processes"," ","Data_People"},
            {"Data_Devices","Devices_Devices","Processes_Devices"," "," "},
            {"Data_Processes","Processes_Devices","Processes_Processes","Processes_Paradigms","Processes_People"},
            {" "," ","Processes_Paradigms"," "," "},
            {"Data_People"," ","Processes_People"," ","People_People"}
    };
    public void init(){
        map.put("Data",0);
        map.put("Devices",1);
        map.put("Processes",2);
        map.put("Paradigms",3);
        map.put("People",4);
    }

}
