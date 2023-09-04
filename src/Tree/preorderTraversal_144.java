package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class preorderTraversal_144 {
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        preorderTraversal(root, list);
        return list;
    }
    private void preorderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) {
            return ;
        }
        list.add(root.val);
        preorderTraversal(root.left, list);
        preorderTraversal(root.right, list);
    }
}
