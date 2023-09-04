package Tree;

import Public.BinaryTree.TreeNode;

public class LevelOrder_minDepth_111 {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int i = minDepth(root.left);
        int i1 = minDepth(root.right);
        if (i == 0 || i1 == 0) {
            return Math.max(i, i1) + 1;
        }
        return Math.min(i, i1) + 1;
    }
}
