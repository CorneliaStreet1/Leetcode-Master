package Tree;

import Public.BinaryTree.TreeNode;

public class SymmetricTree_101 {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        else {
            return Compare(root.left, root.right);
        }
    }
    private boolean Compare(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null && right != null) {
            return false;
        }
        if (left != null && right == null) {
            return false;
        }
        return (left.val == right.val) && Compare(left.left, right.right)  && Compare(left.right, right.left);
    }
}
