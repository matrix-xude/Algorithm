package tree;

import tree.node.TreeNode;
import tree.util.TreePrintUtil;

/**
 * author : xxd
 * date   : 2026/1/5
 * desc   : 平衡二叉搜索树（必须是搜索二叉树，同时任意子树的左右子树高度差不超过1）
 */
public class AVLTree {

    public TreeNode search(TreeNode root, int data) {
        if (root == null || root.data == data) {
            return root;
        }
        if (data < root.data) {
            return search(root.left, data);
        } else {
            return search(root.right, data);
        }
    }

    public TreeNode insert(TreeNode root, int data) {
        if (root == null) {
            return new TreeNode(data, 1);
        }

        if (data < root.data) {
            root.left = insert(root.left, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        } else {  // 相同的数据，不需要处理
            return root;
        }

        // 重新计算高度
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // 平衡检测
        return balanceNode(root);
    }

    public TreeNode delete(TreeNode root, int data) {
        if (root == null) {
            return null;
        }

        if (data < root.data) {
            root.left = delete(root.left, data);
        } else if (data > root.data) {
            root.right = delete(root.right, data);
        } else {
            // 叶子结点
            if (root.left == null && root.right == null) {
                return null;
            }
            // 单子树节点
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            // 双子树节点
            TreeNode min = findMin(root.right);
            root.data = min.data;
            root.right = delete(root.right, min.data);
        }

        // 重新计算高度
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // 平衡检测
        return balanceNode(root);
    }
    
    private TreeNode balanceNode(TreeNode node){
        // 平衡检测
        int br = balance(node);

        // LL型
        if (br > 1 && balance(node.left) >= 0) {
            return rightRotate(node);
        }
        // LR型
        if (br > 1 && balance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RR型
        if (br < -1 && balance(node.right) <= 0) {
            return leftRotate(node);
        }
        // RL型
        if (br < -1 && balance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private int height(TreeNode node) {
        return node == null ? 0 : node.height;
    }

    private int balance(TreeNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // 查找树的最小值节点
    private TreeNode findMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // 右旋操作
    private TreeNode rightRotate(TreeNode root) {
        TreeNode temp = root.left;
        root.left = temp.right; // 完整右旋操作，插入时这里必然为null,但是删除时必要
        temp.right = root;
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        temp.height = Math.max(height(temp.left), height(temp.right)) + 1;
        return temp;
    }

    // 左旋操作
    private TreeNode leftRotate(TreeNode root) {
        TreeNode temp = root.right;
        root.right = temp.left;
        temp.left = root;
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        temp.height = Math.max(height(temp.left), height(temp.right)) + 1;
        return temp;
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        // 构建一棵树
        TreeNode root = tree.create();
        // 增加一个节点
        root = tree.insert(root, 12);
        TreePrintUtil.printHeight(root);
        // 删除节点
        root = tree.delete(root, 12);
        TreePrintUtil.printHeight(root);
        root = tree.delete(root, 10);
        TreePrintUtil.printHeight(root);
        root = tree.delete(root, 14);
        TreePrintUtil.printHeight(root);
    }

    /***
     * 构建一个二叉树用来测试代码,返回根节点
     *         8
     *        / \
     *       3   10
     *      / \    \
     *     1   6    14
     */
    private TreeNode create() {
        AVLTree tree = new AVLTree();
        TreeNode root = tree.insert(null, 8);
        TreePrintUtil.printHeight(root);
        root = tree.insert(root, 10);
        TreePrintUtil.printHeight(root);
        root = tree.insert(root, 14);
        TreePrintUtil.printHeight(root);
        root = tree.insert(root, 3);
        TreePrintUtil.printHeight(root);
        root = tree.insert(root, 1);
        TreePrintUtil.printHeight(root);
        root = tree.insert(root, 6);
        TreePrintUtil.printHeight(root);
        return root;
    }
}
