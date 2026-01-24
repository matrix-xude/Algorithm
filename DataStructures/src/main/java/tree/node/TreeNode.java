package tree.node;

/**
 * author : xxd
 * date   : 2026/1/5
 * desc   : 通用，可以用来实现
 * 1. 二叉搜索树
 * 2. AVL树（平衡二叉搜索树）
 * 3. 红黑树
 */
public class TreeNode {

    public int data; // 整数，用来实现排序
    public TreeNode left, right, parent; // 红黑树需要知道父节点
    public int height; // avl树需要计算高度
    public boolean red; // true:红  black:黑

    public TreeNode(int data) {
        this.data = data;
    }

    public TreeNode(int data, int height) {
        this.data = data;
        this.height = height;
    }

    public TreeNode(int data, TreeNode parent) {
        this(data, parent, true);
    }

    public TreeNode(int data, TreeNode parent, boolean red) {
        this.data = data;
        this.parent = parent;
        this.red = red;
    }
}
