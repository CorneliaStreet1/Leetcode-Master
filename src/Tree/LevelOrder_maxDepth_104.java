package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class LevelOrder_maxDepth_104 {
    //使用层序遍历来做,遍历到最底下一层即可。
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> NodeQueue = new ArrayDeque<>();
        NodeQueue.addLast(root);
        int Depth = 0;
        while (!NodeQueue.isEmpty()) {
            Depth ++;
            int size = NodeQueue.size();
            while (size > 0) {
                TreeNode treeNode = NodeQueue.removeFirst();
                if (treeNode.left != null) {
                    NodeQueue.addLast(treeNode.left);
                }
                if (treeNode.right != null) {
                    NodeQueue.addLast(treeNode.right);
                }
                size --;
            }
        }
        return Depth;
    }
}
