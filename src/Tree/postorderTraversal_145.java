package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class postorderTraversal_145 {
    public List<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        postorderTraversal(root, arrayList);
        return arrayList;
    }
    private void postorderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        postorderTraversal(root.left, list);
        postorderTraversal(root.right, list);
        list.add(root.val);
    }
}
