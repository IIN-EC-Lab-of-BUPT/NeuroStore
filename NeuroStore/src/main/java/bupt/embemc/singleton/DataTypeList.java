package bupt.embemc.singleton;

import java.util.HashMap;

public class DataTypeList {

    public HashMap dataTypelist = new HashMap(){
        {
            put("int","int");
            put("long","bigint");
            put("string","varchar(255)");
            put("bool","bool");
            put("bytes","varchar(255)");
        }
    };

    private DataTypeList(){

    }
    public static DataTypeList getInstance(){return InnerClass.DATA_TYPE_LIST;}

    public static class InnerClass{
        private static final DataTypeList DATA_TYPE_LIST = new DataTypeList();
    }
}
