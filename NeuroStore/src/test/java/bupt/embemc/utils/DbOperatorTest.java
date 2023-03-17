package bupt.embemc.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DbOperatorTest {

    @Test
    public void test(){
        HashMap map = new HashMap<String,String>();
        map.put("CLASS","cy");
        map.put("a#1","f");
        map.put("b#2", 2);
//        SqlGenerator.insert(map);

        System.out.println(map.get ("b#2"));
    }
    @Test
    public void testupdate(){
        HashMap map = new HashMap<>();
        String[][] s = new String[][]{{"a#1","=","f"}};
        map.put("CLASS","cy");
        map.put("a#1","f");
        map.put("b#2", "2");
        map.put("CONDITION",s);
//        DbOperator.update(map);

    }
    @Test
    public void testselect(){
        HashMap map = new HashMap<>();

        map.put("CLASS","cy");
        map.put("a#1","f");
//        DbOperator.select(map);

    }
    @Test
    public void testcreate(){
        HashMap map = new HashMap<>();

        map.put("CLASS","cy");
        map.put("PRIMARYKEY","Did");
        map.put("Did","INT UNSIGNED AUTO_INCREMENT");
//        DbOperator.create(map);

    }
}