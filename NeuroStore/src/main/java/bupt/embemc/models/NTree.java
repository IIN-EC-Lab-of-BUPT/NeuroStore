package bupt.embemc.models;

import java.util.ArrayList;

public class NTree {
    private String className;
    private ArrayList<String> childNodes = new ArrayList<>();

    public NTree() {
    }

    public NTree(String className) {
        this.className = className;
    }

    public NTree(ArrayList<String> childNodes) {
        this.childNodes = childNodes;
    }

    public NTree(String className, ArrayList<String> childNodes) {
        this.className = className;
        this.childNodes = childNodes;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<String> getChildNodes() {
        return childNodes;
    }

    public void addChildNodes(String childNode) {
        this.childNodes.add(childNode);
    }
}
