package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Iterative_Inorder_94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        TreeNode current = root;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.addLast(root);
        while (current != null && !stack.isEmpty()) {
            if (current.left != null) {
                stack.addLast(current.left);
                current = current.left;
            }
            else {
                current = stack.removeLast();
                arrayList.add(current.val);
                current = current.right;
            }
        }
        return arrayList;
    }
}
