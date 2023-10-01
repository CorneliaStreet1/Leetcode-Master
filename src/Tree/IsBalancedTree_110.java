package Tree;

import Public.BinaryTree.TreeNode;

public class IsBalancedTree_110 {
    public boolean isBalanced(TreeNode root) {
        return getHeight(root) >= 0;
    }
    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int Left_height = getHeight(root.left);
        int Right_height = getHeight(root.right);
        if (Left_height < 0 || Right_height < 0 || Math.abs(Right_height -Left_height) > 1) {
            return -1;
        }
        return Math.max(Left_height, Right_height) + 1;
    }
}
