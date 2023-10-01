package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class ValidBSTVerifying_98 {
    public boolean isValidBST(TreeNode root) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        getInOrder(root, arrayList);
        Object[] objects = arrayList.toArray();
        for (int i = 0; i < objects.length - 1; i++) {
            int pre = (Integer)objects[i];
            int next = (Integer)objects[i + 1];
            if (pre >= next) {
                return false;
            }
        }
        return true;
    }
    private void getInOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        getInOrder(root.left, list);
        list.add(root.val);
        getInOrder(root.right, list);
    }
    private boolean isValidTreeHelper(TreeNode root, int Lower, int Upper) {
        if (root == null) {
            return true;
        }
        boolean LeftValid = false;
        if (root.left != null && root.right != null) {
            return root.val > root.left.val && root.val < root.right.val
                    && isValidTreeHelper(root.left, Integer.MIN_VALUE, root.val)
                    && isValidTreeHelper(root.right, root.val, Integer.MAX_VALUE);
        }
        else if (root.left != null && root.right == null) {
            return root.val > root.left.val
                    && isValidTreeHelper(root.left, Integer.MIN_VALUE, root.val);
        }
        else if (root.left == null && root.right != null) {
            return  root.val < root.right.val
                    && isValidTreeHelper(root.right, root.val, Integer.MAX_VALUE);
        }
        else {
            return root.val > Lower && root.val < Upper;
        }
    }

    public static void main(String[] args) {
        //TreeNode treeNode = new TreeNode(5, new TreeNode(4), new TreeNode(6, new TreeNode(3), new TreeNode(7)));
        TreeNode treeNode = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        (new ValidBSTVerifying_98()).isValidBST(treeNode);
    }
}
