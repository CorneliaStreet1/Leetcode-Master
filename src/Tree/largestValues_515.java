package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class largestValues_515 {
    public List<Integer> largestValues(TreeNode root) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (root == null) {
            return arrayList;
        }
        Deque<TreeNode> nodesQueue = new ArrayDeque<>();
        nodesQueue.addLast(root);
        while (!nodesQueue.isEmpty()) {
            int size = nodesQueue.size();
            int Max = Integer.MIN_VALUE;
            while (size > 0) {
                TreeNode treeNode = nodesQueue.removeFirst();
                if (treeNode.val > Max) {
                    Max = treeNode.val;
                }
                if (treeNode.left != null) {
                    nodesQueue.addLast(treeNode.left);
                }
                if (treeNode.right != null) {
                    nodesQueue.addLast(treeNode.right);
                }
                size --;
            }
            arrayList.add(Max);
        }
        return arrayList;
    }
}
