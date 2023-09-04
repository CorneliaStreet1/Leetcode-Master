package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class Recursive_minDepth_111 {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> NodeQueue = new ArrayDeque<>();
        NodeQueue.addLast(root);
        int MinDepth = 0;
        while (!NodeQueue.isEmpty()) {
            MinDepth ++;
            int size = NodeQueue.size();
            while (size > 0) {
                TreeNode treeNode = NodeQueue.removeFirst();
                if (treeNode.left != null) {
                    NodeQueue.addLast(treeNode.left);
                }
                if (treeNode.right != null) {
                    NodeQueue.addLast(treeNode.right);
                }
                if (treeNode.left == null && treeNode.right == null) {
                    return MinDepth;
                }
                size --;
            }
        }
        return MinDepth;
    }
}
