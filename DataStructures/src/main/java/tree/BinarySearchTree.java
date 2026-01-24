package tree;

import tree.node.TreeNode;
import tree.util.TreePrintUtil;

/**
 * author : xxd
 * date   : 2026/1/5
 * desc   : 搜索二叉树
 */
public class BinarySearchTree {

    public TreeNode search(TreeNode root, int data) {
        if (root == null || root.data == data) {
            return root;
        }
        return search(root.data < data ? root.left : root.right, data);
    }

    public TreeNode insert(TreeNode root, int data) {
        if (root == null) {
            return new TreeNode(data);
        }
        if (data < root.data) {
            // 这样写，必须让本函数返回root才能保证逻辑正常
            root.left = insert(root.left, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        }
        // 相同的数据不可插入，不需要处理
        return root;
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
            // 搜索二叉树不记录高度等数据，直接交换data
            root.data = min.data;
            // 因为没有记录父节点，这里还得继续查找删除
            root.right = delete(root.right, min.data);

        }
        return root;
    }

    // 中序遍历
    public void inorder(TreeNode root) {
        if (root == null)
            return;
        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    // 查找树的最小值节点
    private TreeNode findMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        // 构建一棵树
        TreeNode root = tree.create();
        tree.inorder(root);
        System.out.print("\r\n");
        TreePrintUtil.print(root);
        // 增加节点
        root = tree.insert(root, 5);
        TreePrintUtil.print(root);
        // 删除节点
        root = tree.delete(root, 8); // 删除带有2个子树的节点
        TreePrintUtil.print(root);
        root = tree.delete(root, 6);  // 删除带有1个子树的节点
        TreePrintUtil.print(root);
        root = tree.delete(root, 5);  // 删除叶子结点
        TreePrintUtil.print(root);
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
        BinarySearchTree tree = new BinarySearchTree();
        TreeNode root = tree.insert(null, 8);
        tree.insert(root, 10);
        tree.insert(root, 3);
        tree.insert(root, 14);
        tree.insert(root, 1);
        tree.insert(root, 6);
        return root;
    }

}
