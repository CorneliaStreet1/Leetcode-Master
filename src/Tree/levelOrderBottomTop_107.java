package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class levelOrderBottomTop_107 {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<List<Integer>> temp = new ArrayDeque<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.addLast(root);
        while (! nodeQueue.isEmpty()) {
            int size = nodeQueue.size();
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (size > 0) {
                TreeNode treeNode = nodeQueue.removeFirst();
                if (treeNode.left != null) {
                    nodeQueue.addLast(treeNode.left);
                }
                if (treeNode.right != null) {
                    nodeQueue.addLast(treeNode.right);
                }
                arrayList.add(treeNode.val);
                size --;
            }
            temp.addFirst(arrayList);
        }
        result.addAll(temp);
        return result;
    }
}
