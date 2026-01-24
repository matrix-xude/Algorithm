package tree.util;

import tree.node.TreeNode;

/**
 * author : xxd
 * date   : 2026/1/5
 * desc   : 在控制台打印各种树
 */
public class TreePrintUtil {

    /*** 层级打印二叉树（左子树在上，右子树在下）
     * └── 指向右子树，├──指向左子树（root节点也显示成右子树）
     * <p>
     * └── 8
     *     ├── 10
     *     │   ├── 14
     *     └── 3
     *         ├── 6
     *         └── 1
     */
    public static void print(TreeNode root) {
        print(root, "", true);
    }

    public static void printHeight(TreeNode root) {
        printHeight(root, "", true);
    }

    public static void printColor(TreeNode root) {
        printColor(root, "", true);
    }

    private static void print(TreeNode node, String prefix, boolean isTail) {
        if (node == null) return;

        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.data);

        if (node.left != null || node.right != null) {
            if (node.left != null)
                print(node.left, prefix + (isTail ? "    " : "│   "), false);
            if (node.right != null)
                print(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    private static void printHeight(TreeNode node, String prefix, boolean isTail) {
        if (node == null) return;

        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.data + "(h=" + node.height + ")");

        if (node.left != null || node.right != null) {
            if (node.left != null)
                printHeight(node.left, prefix + (isTail ? "    " : "│   "), false);
            if (node.right != null)
                printHeight(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    private static void printColor(TreeNode node, String prefix, boolean isTail) {
        if (node == null) return;

        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.data + "(c=" + (node.red ? "r" : "b") + ")");

        if (node.left != null || node.right != null) {
            if (node.left != null)
                printColor(node.left, prefix + (isTail ? "    " : "│   "), false);
            if (node.right != null)
                printColor(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
