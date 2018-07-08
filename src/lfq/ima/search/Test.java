package lfq.ima.search;

public class Test {

    public static void main (String args[])
    {
        Tree tree = new Tree();
        TreeUtil.insert(tree,1);
        TreeUtil.insert(tree,5);
        TreeUtil.insert(tree,0);
        TreeUtil.insert(tree,9);
        TreeUtil.insert(tree,3);
        TreeUtil.insert(tree,2);
        TreeUtil.insert(tree,4);
        TreeUtil.delete(tree,9);
        TreeUtil.middleSearch(tree);
        System.out.println();
        TreeUtil.midddleSearchUseStack(tree);
      //  System.out.print("\n"+TreeUtil.findNode(tree,3).getValue().toString());
    }
}
