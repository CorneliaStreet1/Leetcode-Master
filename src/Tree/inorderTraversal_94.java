package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class inorderTraversal_94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        inorderTraversal(root, arrayList);
        return arrayList;
    }
    private void  inorderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left, list);
        list.add(root.val);
        inorderTraversal(root.right, list);
    }
}
