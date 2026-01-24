package tree;

import tree.node.TreeNode;
import tree.util.TreePrintUtil;

/**
 * author : xxd
 * date   : 2026/1/5
 * desc   : 红黑树首先是搜索二叉树，同时满足5个特点
 * 1. 节点是红 or 黑
 * 2. 跟节点是黑色
 * 3. 叶子结点NIL是黑色（这里的叶子结点是指所有空节点,java中的null）
 * 4. 红色节点不能相邻
 * 5. 任一节点到其所有后代 NIL 节点的黑色节点数相同
 */
public class RedBlackTree {

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
            // 直接创建根节点
            return new TreeNode(data, null, false);
        }

        // 插入节点
        TreeNode insert = root;
        while (true) {
            if (data < insert.data) {
                if (insert.left == null) {
                    insert.left = new TreeNode(data, insert);
                    insert = insert.left;
                    break;
                } else {
                    insert = insert.left;
                }
            } else if (data > insert.data) {
                if (insert.right == null) {
                    insert.right = new TreeNode(data, insert);
                    insert = insert.right;
                    break;
                } else {
                    insert = insert.right;
                }
            } else {
                // 相同数据，不需要处理
                return root;
            }
        }

        // 修正红黑树
        return fixUpInsertNode(insert);
    }

    public TreeNode delete(TreeNode root, int data) {
        if (root == null) {
            return null;
        }

        // 查找需要删除的节点
        TreeNode delete = root;
        while (true) {
            if (data < delete.data) {
                // 删除数据不存在，直接返回
                if (delete.left == null) {
                    return root;
                }
                delete = delete.left;
            } else if (data > delete.data) {
                // 删除数据不存在，直接返回
                if (delete.right == null) {
                    return root;
                }
                delete = delete.right;
            } else {  // 找到了需要删除的点
                break;
            }
        }

        // 删除找到的节点
        // 1.删除的节点有左右子树
        if (delete.left != null && delete.right != null) {
            // 找到右子树的最小值节点,交换数据
            TreeNode min = findMin(delete.right);
            int tempData = min.data;
            min.data = delete.data;
            delete.data = tempData;
            // 删除节点重新赋值
            delete = min;
        }

        // 2.当前删除节点只有0或1棵子树
        if (delete.left != null || delete.right != null) {
            // 如有只有1棵子树，只能黑-红,直接删除该节点，子节点代替，并且变黑（如果根节点是红色，违反了黑路同性质，不存在该情况）
            boolean isLeft = delete.left != null;
            TreeNode parent = delete.parent;

            // 删除的是根节点，并且只有1个子节点
            if (parent == null) {
                TreeNode replacementNode = isLeft ? delete.left : delete.right;
                replacementNode.red = false;
                replacementNode.parent = null;
                return replacementNode;
            }

            // 删除的不是根节点，并且只有1个子节点，删除替换后返回根节点
            boolean isParentLeft = parent.left == delete; // 删除节点是否父节点的左子树
            TreeNode replacementNode = isLeft ? delete.left : delete.right;
            replacementNode.red = false;
            replacementNode.parent = parent;
            if (isParentLeft) {
                parent.left = replacementNode;
            } else {
                parent.right = replacementNode;
            }
            return root;
        }

        // 3.当前删除节点是叶子结点
        TreeNode parent = delete.parent;
        // a. 叶子结点是根节点，返回null
        if (parent == null) {
            return null;
        }

        // b. 叶子结点不是根节点，先删除该节点（把该节点的父节点指向该节点的引用赋值为null）
        boolean isLeft = parent.left == delete;
        if (isLeft) {
            parent.left = null;
        } else {
            parent.right = null;
        }

        // 此时删除节点还有指向原父节点的引用，但是原父节点没有指向该节点的引用
        if (delete.red) {
            // 删除的叶子结点是红色，无影响
            return root;
        } else {
            // 删除的叶子结点是黑色(将其看为双黑节点，方便后续统一处理)，情况比较复杂，需要根据兄弟节点进行区分（此时兄弟节点一定不为null，否则父节点违反黑路同性质）
            return fixUpDoubleBlackNode(delete);
        }

    }

    /**
     * 修正双黑节点，此时双黑一定有兄弟节点，否则违反黑路同性质
     *
     * @param node 该节点为双黑（该节点的父节点可能已经没有指向该节点的引用）
     * @return 修正后的根节点
     */
    private TreeNode fixUpDoubleBlackNode(TreeNode node) {
        TreeNode parent = node.parent;
        // 根节点双黑，直接变正常黑节点即可
        if (parent == null) {
            return node;
        }

        // 双黑节点原来为红色，直接变黑色即可
        if (node.red){
            node.red = false;
            return findRoot(node);
        }

        // 当前节点是否父节点的左子树（该节点的父节点可能已经没有指向该节点的引用）
        boolean isLeft = parent.left == null || parent.left == node;
        TreeNode sibling = isLeft ? parent.right : parent.left; // 兄弟节点一定不为null

        // 1. 兄弟节点为红色（此时parent一定为黑，可以在parent节点下调整到保持之前的黑路同）
        if (sibling.red) {
            if (isLeft) {
                leftRotate(parent);
                sibling.red = false; // 新根节点一定是黑色
                parent.right.red = true; // 新根节点左子树的右子树，改为红色
            } else {
                rightRotate(parent);
                sibling.red = false;
                parent.left.red = true; // 新根节点左子树的右子树，改为红色
            }
            return findRoot(sibling);
        }

        // 2.此时兄弟节点为黑色
        boolean isSiblingHasRedChild = (sibling.left != null && sibling.left.red) || (sibling.right != null && sibling.right.red);
        // 兄弟节点没有红色子节点，不能在该parent下调整到保持之前的黑路同
        if (!isSiblingHasRedChild) {
            sibling.red = true; // 兄弟节点变红，双黑节点上移到parent,继续迭代调整
            return fixUpDoubleBlackNode(parent);
        }

        // 此时兄弟节点有红色子节点，可以在该parent下调整到保持之前的黑路同
        TreeNode newRoot = null;
        if (isLeft) {
            if (sibling.right != null && sibling.right.red) { // 兄弟节点的右子树是红色,不需要考虑左子树
                // RR型 (先变色再旋转，代码好写)
                sibling.right.red = false; // 将此节点变为sibling节点的颜色，必然是黑色
                sibling.red = parent.red;
                parent.red = false;
                newRoot = leftRotate(parent);
            } else {  // 兄弟节点的右子树不是红色，左子树是红色(这里需要按照保证黑路同思考，sibling左子树的左右子树最终位置)
                // RL型
                sibling.left.red = parent.red;
                parent.red = false;
                rightRotate(sibling);
                newRoot = leftRotate(parent);
            }
        } else {
            if (sibling.left != null && sibling.left.red) { // 兄弟节点的左子树是红色,不需要考虑右子树
                // LL型 (先变色再旋转，代码好写)
                sibling.left.red = false; // 将此节点变为sibling节点的颜色，必然是黑色
                sibling.red = parent.red;
                parent.red = false;
                newRoot = rightRotate(parent);
            } else {  // 兄弟节点的左子树不是红色，右子树是红色(这里需要按照保证黑路同思考，sibling右子树的左右子树最终位置)
                // LR型
                sibling.right.red = parent.red;
                parent.red = false;
                leftRotate(sibling);
                newRoot = rightRotate(parent);
            }
        }
        return findRoot(newRoot);

    }

    /**
     * 插入红节点后，修正破坏的红黑树性质
     * 1. 根必黑
     * 2. 不红红
     * 3. 黑路同
     *
     * @param node 从该节点往上检测
     * @return 返回插入后的根节点（因为根节点可能变化）
     */
    private TreeNode fixUpInsertNode(TreeNode node) {
        TreeNode parent = node.parent; // 父节点
        TreeNode grandParent = parent == null ? null : parent.parent; // 爷爷节点

        // 当前为根节点
        if (parent == null) {
            node.red = false; // 根必黑
            return node;
        }

        // 红色相连
        if (node.red && parent.red) {
            // 1.检测节点的父节点即为根节点，没有叔叔节点（插入时不可能出现此场景，但是迭代调整中可能出现）
            if (grandParent == null) {
                parent.red = false; // 根必黑
                return parent;
            }

            TreeNode uncle = uncle(node);
            // 2.叔叔节点是红色
            if (uncle != null && uncle.red) {
                parent.red = false;
                uncle.red = false;
                grandParent.red = true;
                // 递归检测爷爷节点
                fixUpInsertNode(grandParent);
                return findRoot(node);
            }

            // 3.叔叔节点是黑色(插入时必然是null叶子结点，迭代过程中可以不为null)
            boolean isFatherLeft = node == parent.left; // 当前节点是否父节点的左子树
            boolean isGrandeFatherLeft = parent == grandParent.left; // 父节点是否爷爷节点的左子树

            TreeNode newRoot = grandParent;
            // LL型
            if (isGrandeFatherLeft && isFatherLeft) {
                newRoot = rightRotate(grandParent);
                grandParent.red = true;
                parent.red = false;
            }

            // LR型
            if (isGrandeFatherLeft && !isFatherLeft) {
                leftRotate(parent);
                newRoot = rightRotate(grandParent);
                grandParent.red = true;
                node.red = false;
            }

            // RR型
            if (!isGrandeFatherLeft && !isFatherLeft) {
                newRoot = leftRotate(grandParent);
                grandParent.red = true;
                parent.red = false;
            }

            // RL型
            if (!isGrandeFatherLeft && isFatherLeft) {
                rightRotate(parent);
                newRoot = leftRotate(grandParent);
                grandParent.red = true;
                node.red = false;
            }

            return findRoot(newRoot);
        }

        // 因为插入的节点默认红色，不会违反黑路同性质，剩下情况不需要处理
        return findRoot(node);
    }

    // 左旋传入的节点，返回新的根节点
    private TreeNode leftRotate(TreeNode node) {
        // 因为红黑树包含3个自身的引用，左子树、右子树、父节点树，所以必须保证这3个节点的引用都修改完成

        TreeNode parent = node.parent; // 可以为null，表示当前node为根节点
        boolean isLeft = parent != null && parent.left == node; // 区分左右子树

        TreeNode temp = node.right;
        // node变为新root的左子树
        node.right = temp.left;
        node.parent = temp;

        temp.left = node;
        temp.parent = parent;

        // 原根节点的父节点子树指向也要修改
        if (parent != null) {
            if (isLeft)
                parent.left = temp;
            else
                parent.right = temp;
        }

        return temp;
    }

    // 右旋传入的节点，返回新的根节点
    private TreeNode rightRotate(TreeNode node) {
        // 因为红黑树包含3个自身的引用，左子树、右子树、父节点树，所以必须保证这3个节点的引用都修改完成

        TreeNode parent = node.parent; // 可以为null，表示当前node为根节点
        boolean isLeft = parent != null && parent.left == node; // 区分左右子树

        TreeNode temp = node.left;
        // node变为新root的左子树
        node.left = temp.right;
        node.parent = temp;

        temp.right = node;
        temp.parent = parent;

        // 原根节点的父节点子树指向也要修改
        if (parent != null) {
            if (isLeft)
                parent.left = temp;
            else
                parent.right = temp;
        }

        return temp;
    }

    private TreeNode findRoot(TreeNode node) {
        while (node.parent != null) {
            node = node.parent;
        }
        return node;
    }

    // 查找叔叔节点，非法情况一律返回null
    private TreeNode uncle(TreeNode node) {
        if (node == null || node.parent == null || node.parent.parent == null) {
            return null;
        }

        if (node.parent.parent.left == node.parent) {
            return node.parent.parent.right;
        } else {
            return node.parent.parent.left;
        }
    }

    // 查找树的最小值节点
    private TreeNode findMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
//        TreeNode root = tree.create();
        // 测试删除
        TreeNode root = tree.testDelete();
        root = tree.delete(root, 18);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 25);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 15);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 6);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 13);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 37);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 27);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 17);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 34);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 9);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 10);
        TreePrintUtil.printColor(root);
        root = tree.delete(root, 23);
        System.out.println("null_");
        TreePrintUtil.printColor(root);
    }

    /***
     * 构建一个二叉树用来测试代码,返回根节点
     * 依次插入 17 18 23 34 27 15 9 6 8 5 25
     * 图形演示来自B站：<a href="https://www.bilibili.com/video/BV1Xm421x7Lg?spm_id_from=333.788.videopod.sections&vd_source=f479c1c07f68de63a2fa82821d24fb88">...</a>
     *           15
     *         /    \
     *       8      18
     *      / \    /  \
     *     6   9  17   14
     *    /          /   \
     *   5          23    34
     *               \
     *                25
     */
    private TreeNode create() {
        RedBlackTree tree = new RedBlackTree();
        TreeNode root = tree.insert(null, 17);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 18);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 23);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 34);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 27);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 15);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 9);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 6);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 8);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 5);
        TreePrintUtil.printColor(root);
        root = tree.insert(root, 25);
        TreePrintUtil.printColor(root);
        return root;
    }

    /***
     * 构建一个二叉树用来测试红黑树删除,返回根节点
     * 已有红黑树如下 18 25 15 6 13 37 27 34 9 10 23
     * 图形演示来自B站：<a href="https://www.bilibili.com/video/BV16m421u7Tb?spm_id_from=333.788.videopod.sections&vd_source=f479c1c07f68de63a2fa82821d24fb88">...</a>
     *           15
     *         /    \
     *       9      18
     *      / \    /  \
     *     6  13  17   27(R)
     *        /       /  \
     *      10(R)   23   34
     *               \     \
     *              25(R)  37(R)
     */
    private TreeNode testDelete() {
        RedBlackTree tree = new RedBlackTree();
        // 直接构建树，node后的数字表示 行数+个数， 21表示第二行第一个
        // 第一行
        TreeNode root = new TreeNode(15, null, false);
        // 第二行
        TreeNode node21 = new TreeNode(9, root, false);
        TreeNode node22 = new TreeNode(18, root, false);
        root.left = node21;
        root.right = node22;
        // 第三行
        TreeNode node31 = new TreeNode(6, node21, false);
        TreeNode node32 = new TreeNode(13, node21, false);
        node21.left = node31;
        node21.right = node32;
        TreeNode node33 = new TreeNode(17, node22, false);
        TreeNode node34 = new TreeNode(27, node22, true);
        node22.left = node33;
        node22.right = node34;
        // 第四行
        TreeNode node41 = new TreeNode(10, node32, true);
        node32.left = node41;
        TreeNode node42 = new TreeNode(23, node34, false);
        TreeNode node43 = new TreeNode(34, node34, false);
        node34.left = node42;
        node34.right = node43;
        // 第五行
        TreeNode node51 = new TreeNode(25, node42, true);
        node42.right = node51;
        TreeNode node52 = new TreeNode(37, node43, true);
        node43.right = node52;

        TreePrintUtil.printColor(root);

        return root;
    }

}
