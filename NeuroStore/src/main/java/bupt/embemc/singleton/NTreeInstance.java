package bupt.embemc.singleton;

import bupt.embemc.models.NTree;

public class NTreeInstance {
    private NTree tree;

    public static NTreeInstance getInstance(){return InnerClass.N_TREE_INSTANCE;}

    public static class InnerClass{
        private static final NTreeInstance N_TREE_INSTANCE = new NTreeInstance();
    }
}
