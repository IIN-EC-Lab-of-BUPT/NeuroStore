package bupt.embemc.operators.util;

import java.util.Map;

public class DLinkedNode {
        String key;
        Map value;
        DLinkedNode prev;
        DLinkedNode next;
        public Map getValue(){return value;}
        public DLinkedNode() {}
        public DLinkedNode(String _key, Map _value) {key = _key; value = _value;}
    }