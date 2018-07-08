package lfq.ima.avl;

public class Test {

    public static void main (String args[])
    {
        TreeUtil root = new TreeUtil(null);
        root.insert(3);
        root.insert(1);
        root.insert(12);
        root.insert(5);
        root.insert(4);
        root.delete(5);
        root.middleSearch();
    }
}
