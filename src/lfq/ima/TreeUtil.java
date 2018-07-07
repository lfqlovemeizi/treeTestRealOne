package lfq.ima;

import java.util.Stack;

public class TreeUtil {

    //中序遍历
    public static void middleSearch(Tree tree)
    {

        if(tree.getlChild()!=null)
        {middleSearch(tree.getlChild());}
        System.out.print(tree.getValue());
        if(tree.getrChild()!=null)
        {middleSearch(tree.getrChild());}
    }

    //使用非递归方式中序遍历
    public static void midddleSearchUseStack(Tree tree)
    {
        Stack<Tree> stack = new Stack<>();
        Tree rollTree = tree;
        while(rollTree != null || stack.size() != 0)
        {
            if(rollTree != null)
            {
                stack.push(rollTree);
                rollTree = rollTree.getlChild();
            }else
            {
                rollTree = stack.pop();
                System.out.print(rollTree.getValue());
                rollTree = rollTree.getrChild();
            }
        }

    }

    public static void delete(Tree tree,int value)
    {
        //查询到的节点的父节点
        Tree parent = null;
        //查询到的节点
        Tree searchTree = tree;
        //当节点不为空的时候开始循环
        while(searchTree != null)
        {
            //如果节点的值等于查询值，跳出循环，之后的逻辑就是一个树查找的逻辑
            if(searchTree.getValue()==value)
            {
                break;
            }
            else if(searchTree.getValue() > value)
            {
                parent = searchTree;
                searchTree = searchTree.getlChild();
            }
            else if(searchTree.getValue() < value)
            {
                parent = searchTree;
                searchTree = searchTree.getrChild();
            }
        }

        //查询到的节点是父节点的左节点与否
        boolean parentLeftFlag = false;
        //查询到的节点的父节点是否存在
        boolean hasParent = true;
        //查询到的节点的左子树存在与否
        boolean leftFlag = false;
        //查询到的节点的右子树存在与否
        boolean rightFlag = false;


        if(parent == null)
        {
            hasParent = false;
        }
        else if(parent.getrChild() == searchTree)
        {
            parentLeftFlag = false;
        }
        else{
            parentLeftFlag = true;
        }


        if(searchTree == null)
        {
            return;
        }
        if(searchTree.getlChild() != null){
            leftFlag = true;
        }
        if(searchTree.getrChild() != null){
            rightFlag = true;
        }

        if(!leftFlag && !rightFlag)
        {
            if(hasParent)
            {
                if(parentLeftFlag)
                {
                    parent.setlChild(null);
                }
                else{
                    parent.setrChild(null);
                }
            }
            else{
                searchTree.setValue(null);
            }
        }
        else if(!leftFlag || !rightFlag)
        {
            if(hasParent)
            {
                if(parentLeftFlag)
                {
                    if(leftFlag)
                    {
                        parent.setlChild(searchTree.getlChild());
                    }
                    else {
                        parent.setlChild(searchTree.getrChild());
                    }
                }
                else {
                    if(leftFlag)
                    {
                        parent.setrChild(searchTree.getlChild());
                    }
                    else {
                        parent.setrChild(searchTree.getrChild());
                    }
                }
            }
            else
            {
                if(leftFlag){
                    searchTree.setValue(searchTree.getlChild().getValue());
                    searchTree.setrChild(searchTree.getlChild().getrChild());
                    searchTree.setlChild(searchTree.getlChild().getlChild());
                }
                else
                {
                    searchTree.setValue(searchTree.getrChild().getValue());
                    searchTree.setlChild(searchTree.getrChild().getlChild());
                    searchTree.setrChild(searchTree.getrChild().getrChild());
                }
            }
        }
        else if(leftFlag && rightFlag){
            Tree minTree = searchTree.getrChild();
            while (minTree.getlChild() != null)
            {
                minTree = minTree.getlChild();
            }
            Integer minTreeValue = minTree.getValue();
            delete(searchTree,minTreeValue);
            searchTree.setValue(minTreeValue);
        }
    }

    //以查找二叉树的规则插入，小的在左，大的在右
    public static void insert(Tree tree,int value)
    {
        if(tree.getValue() == null)
        {
            tree.setValue(value);
        }
        else if(tree.getValue() < value)
        {
            if(tree.getrChild()!=null)
            {
                insert(tree.getrChild(),value);
            }
            else {
                tree.setrChild(new Tree());
                insert(tree.getrChild(),value);
            }
        }
        else if(tree.getValue() > value)
        {
            if(tree.getlChild()!=null)
            {
                insert(tree.getlChild(),value);
            }
            else {
                tree.setlChild(new Tree());
                insert(tree.getlChild(),value);
            }
        }
    }

    public static Tree findNode(Tree tree,int value)
    {
        if(tree.getValue() == value)
        {
            return tree;
        }
        else if(tree.getValue() < value){
            if(tree.getrChild() != null)
            {
                return findNode(tree.getrChild(),value);
            }
            else {
                return null;
            }
        }
        else if(tree.getValue() > value){
            if(tree.getlChild() != null)
            {
                return findNode(tree.getlChild(),value);
            }
            else {
                return null;
            }
        }
        return null;
    }
}
