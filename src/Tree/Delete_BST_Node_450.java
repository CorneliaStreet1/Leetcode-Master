package Tree;

import Public.BinaryTree.TreeNode;

public class Delete_BST_Node_450 {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
            return root;
        }
        else if (key > root.val) {
            root.right = deleteNode(root.right, key);
            return root;
        }
        else {
            if (root.left == null && root.right == null) {
                return null;
            }
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            TreeNode MostLeft = root.right;
            while (MostLeft.left != null) {
                MostLeft = MostLeft.left;
            }
            root.right = deleteNode(root.right, MostLeft.val);
            MostLeft.left = root.left;
            MostLeft.right = root.right;
            return MostLeft;
        }
    }
}
