package Tree;

import Public.BinaryTree.TreeNode;

public class MergeTwoTrees_617 {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return null;
        }
        else if (root1 != null && root2 == null) {
            return root1;
        }
        else if (root1 == null) {
            return root2;
        }
        else {
            TreeNode treeNode = new TreeNode(root1.val + root2.val);
            treeNode.left = mergeTrees(root1.left, root2.left);
            treeNode.right = mergeTrees(root1.right, root2.right);
            return treeNode;
        }
    }
}
