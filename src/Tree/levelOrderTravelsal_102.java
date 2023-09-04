package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class levelOrderTravelsal_102 {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> NodeQueue = new LinkedList<>();
        NodeQueue.addLast(root);
        while (!NodeQueue.isEmpty()) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            int size = NodeQueue.size();
            while (size > 0) {
                TreeNode treeNode = NodeQueue.removeFirst();
                if (treeNode.left != null) {
                    NodeQueue.addLast(treeNode.left);
                }
                if (treeNode.right != null) {
                    NodeQueue.addLast(treeNode.right);
                }
                arrayList.add(treeNode.val);
                size --;
            }
            result.add(arrayList);
        }
        return result;
    }

    public static void main(String[] args) {
        // TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        TreeNode root = new TreeNode(3);
        System.out.println((new levelOrderTravelsal_102()).levelOrder(null));
    }
}
