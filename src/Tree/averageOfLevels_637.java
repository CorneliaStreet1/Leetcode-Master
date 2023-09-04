package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class averageOfLevels_637 {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> averages = new ArrayList<>();
        if (root == null) {
            return averages;
        }
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.addLast(root);
        while (! nodeQueue.isEmpty()) {
            double Sum = 0;
            int size = nodeQueue.size();
            int size1 = size;
            while (size > 0) {
                TreeNode treeNode = nodeQueue.removeFirst();
                if (treeNode.left != null) {
                    nodeQueue.addLast(treeNode.left);
                }
                if (treeNode.right != null) {
                    nodeQueue.addLast(treeNode.right);
                }
                Sum += treeNode.val;
                size --;
            }
            averages.add(Sum / size1 );
        }
        return averages;
    }
}
