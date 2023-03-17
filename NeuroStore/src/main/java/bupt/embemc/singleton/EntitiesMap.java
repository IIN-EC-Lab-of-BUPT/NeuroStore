package bupt.embemc.singleton;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class EntitiesMap {
    public HashMap entities = new HashMap();

    private EntitiesMap(){

    }
    public static EntitiesMap getInstance(){return InnerClass.ENTITIES_MAP;}

    public static class InnerClass{
        private static final EntitiesMap ENTITIES_MAP = new EntitiesMap();
    }

}
