package bupt.embemc.operators.util;

public class ABCCircle {
    class Node{
        char value;
        Node next;
        public Node(char value){
            this.value=value;
        }
    }
    {
        Node head = new Node((char) 65);
        Node pre = head;
        for(int i = 66;i <= 90;i++){
            Node node = new Node((char) i);
            pre.next = node;
            pre = node;
        }
        pre.next = head;
        concurrentNode = head;

    }
    private Node concurrentNode;
    public char getAlphabet(){
        char result = concurrentNode.value;
        concurrentNode = concurrentNode.next;
        return result;
    }

}
