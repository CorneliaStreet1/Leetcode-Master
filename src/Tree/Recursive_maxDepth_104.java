package Tree;

import Public.BinaryTree.TreeNode;

public class Recursive_maxDepth_104 {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int LeftDepth = maxDepth(root.left);
        int RightDepth = maxDepth(root.right);
        return Math.max(LeftDepth, RightDepth) + 1;
    }
}
