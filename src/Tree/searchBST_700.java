package Tree;

import Public.BinaryTree.TreeNode;

public class searchBST_700 {
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (val < root.val) {
            return searchBST(root.left, val);
        }
        if (val > root.val) {
            return searchBST(root.right, val);
        }
        return root;
    }
}
