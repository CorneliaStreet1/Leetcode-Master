package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Iterative_Preorder_144 {
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> ret = new ArrayList<>();
        Deque<TreeNode> treeNodes = new ArrayDeque<>();
        treeNodes.addLast(root);
        while (!treeNodes.isEmpty()) {
            TreeNode treeNode = treeNodes.removeLast();
            if (treeNode.right != null) {
                treeNodes.addLast(treeNode.right);
            }
            if (treeNode.left != null) {
                treeNodes.addLast(treeNode.left);
            }
            ret.add(treeNode.val);
        }
        return ret;
    }
}
