package lfq.ima.avl;



public class TreeUtil {
    //将根节点定义为成员变量
    public Tree root;


    public TreeUtil(Tree root) {
        this.root = root;
    }

    //求两个整数的最大值
    public static int max(int a,int b)
    {
        return a > b ? a : b;
    }

    //当为“左左”情况的时候，发生的单旋转
    public void singleRollLeft()
    {
        //将临时右子树设置为root
        Tree rightTree = root;
        //获取root的左子树
        Tree leftTree = root.getlChild();
        //获取原左子树的右叶子
        Tree rightOne = leftTree.getrChild();
        //将root置为左子树
        root = leftTree;
        //将右子树的左节点变为原左子树的右叶子
        rightTree.setlChild(rightOne);
        root.setrChild(rightTree);
        //高度调整，由于只有原来的左子树和原来的父节点发生了变更，所以只修改两个节点的高度
        root.getrChild().height = max(root.getrChild().getlChild().height,root.getrChild().getrChild().height)+1;
        root.height = max(root.getlChild().height,root.getrChild().height)+1;
    }
    //当为“右右”情况的时候，发生的单旋转
    public void singleRollRight()
    {
        //将临时左子树设置为root
        Tree leftTree = root;
        //获取root的右子树
        Tree rightTree = root.getrChild();
        //获取原右子树的左叶子
        Tree leftOne = rightTree.getlChild();
        //将root置为右子树
        root = rightTree;
        //将左子树的右节点变为原右子树的左叶子
        leftTree.setrChild(leftOne);
        root.setlChild(leftTree);
        //高度调整
        root.getlChild().height = max(root.getlChild().getlChild().height,root.getlChild().getrChild().height)+1;
        root.height = max(root.getlChild().height,root.getrChild().height)+1;
    }

    //当碰到左右的情况
    public void doubleRollLR()
    {
        TreeUtil leftRoot = new TreeUtil(root.getlChild());
        leftRoot.singleRollRight();
        singleRollLeft();
    }

    //当碰到右左的情况
    public void doubleRollRL()
    {
        TreeUtil rightRoot = new TreeUtil(root.getrChild());
        rightRoot.singleRollLeft();
        singleRollRight();
    }

    //中序遍历
    public void middleSearch(){
        if(root.getlChild() != null)
        {
            new TreeUtil(root.getlChild()).middleSearch();
        }
        System.out.println(root.getValue());
        if(root.getrChild() != null)
        {
            new TreeUtil(root.getrChild()).middleSearch();
        }
    }
    //插入
    public void insert(int value)
    {
        if(root == null)
        {
            root = new Tree();
            root.setValue(value);
        }
        else if(value == root.getValue())
        {
            return;
        }
        else if(value > root.getValue()){
            TreeUtil rightRoot = new TreeUtil(root.getrChild());
            rightRoot.insert(value);
            root.setrChild(rightRoot.getRoot());
            int leftHeight = 0;
            int rightHeight = 0;
            if(root.getrChild() != null)
            {
                rightHeight = root.getrChild().height;
            }
            if(root.getlChild() != null)
            {
                leftHeight = root.getlChild().height;
            }
            if(leftHeight+ 2 == rightHeight )
            {
                if(value < root.getrChild().getValue())
                {
                    doubleRollRL();
                }
                else{
                    singleRollRight();
                }
            }
        }
        else
        {
            TreeUtil leftRoot = new TreeUtil(root.getlChild());
            leftRoot.insert(value);
            root.setlChild(leftRoot.getRoot());
            int leftHeight = 0;
            int rightHeight = 0;
            if(root.getrChild() != null)
            {
               rightHeight = root.getrChild().height;
            }
            if(root.getlChild() != null)
            {
                leftHeight = root.getlChild().height;
            }
            if(rightHeight+ 2 == leftHeight )
            {
                if(value > root.getlChild().getValue())
                {
                    doubleRollLR();
                }
                else{
                    singleRollLeft();
                }
            }
        }
    }

    //删除
    public void delete(int value)
    {
        if(root == null)
        {
            return;
        }
        if(value > root.getValue())
        {
            TreeUtil rightRoot = new TreeUtil(root.getrChild());
            rightRoot.delete(value);
            root.setrChild(rightRoot.getRoot());
            int leftHeight = 0;
            int rightHeight = 0;
            if(root.getrChild() != null)
            {
                rightHeight = root.getrChild().height;
            }
            if(root.getlChild() != null)
            {
                leftHeight = root.getlChild().height;
            }
            if(rightHeight+ 2 == leftHeight )
            {
                if(value > root.getlChild().getValue())
                {
                    doubleRollLR();
                }
                else{
                    singleRollLeft();
                }
            }
        }
        else if(value < root.getValue())
        {
            TreeUtil leftRoot = new TreeUtil(root.getlChild());
            leftRoot.delete(value);
            root.setlChild(leftRoot.getRoot());
            int leftHeight = 0;
            int rightHeight = 0;
            if(root.getrChild() != null)
            {
                rightHeight = root.getrChild().height;
            }
            if(root.getlChild() != null)
            {
                leftHeight = root.getlChild().height;
            }
            if(leftHeight+ 2 == rightHeight )
            {
                if(value < root.getrChild().getValue())
                {
                    doubleRollRL();
                }
                else{
                    singleRollRight();
                }
            }
        }
        else {
            //判断删除节点是否存在左子树
            boolean hasLeft = false;
            //判断删除节点是否存在右子树
            boolean hasRight = false;
            if(root.getlChild() != null)
            {
                hasLeft = true;
            }
            if(root.getrChild() != null)
            {
                hasRight = true;
            }
            if(!hasLeft && !hasRight)//不包含左子树以及右子树
            {
                root = null;
            }
            else if(!hasLeft)//只包含右子树
            {
                root = root.getrChild();
            }
            else if (!hasRight)
            {
                root = root.getlChild();
            }
            else {
                Tree rollTree = root.getrChild();
                while(rollTree.getlChild() != null)
                {
                    rollTree = rollTree.getlChild();
                }
                root.setValue(rollTree.getValue());
                TreeUtil rightRoot = new TreeUtil(root.getrChild());
                rightRoot.delete(rollTree.getValue());
                root.setrChild(rightRoot.getRoot());
            }
        }

    }

    public Tree getRoot() {
        return root;
    }

    public void setRoot(Tree root) {
        this.root = root;
    }
}
