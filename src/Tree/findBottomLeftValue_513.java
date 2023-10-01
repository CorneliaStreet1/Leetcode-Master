package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class findBottomLeftValue_513 {
    int depth;
    int result;
    public int findBottomLeftValue(TreeNode root) {
        dfs(root, 1);
        return result;
    }
    private void dfs(TreeNode root, int Depth) {
        if (root == null) {
            return;
        }
        if (Depth > depth) {
            depth = Depth;
            result = root.val;
        }
        dfs(root.left, Depth + 1);
        dfs(root.right, Depth + 1);
    }
    public int findBottomLeftValue_LevelOrder(TreeNode root) {
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        int ret = root.val;
        nodeQueue.addLast(root);
        int Level = 0;
        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();
            Level ++;
            while (size > 0) {
                TreeNode treeNode = nodeQueue.removeFirst();
                if (treeNode.right != null) {
                    nodeQueue.addLast(treeNode.right);
                }
                if (treeNode.left != null) {
                    nodeQueue.addLast(treeNode.left);
                }
                size --;
            }
            if (!nodeQueue.isEmpty()) {
                ret = nodeQueue.getLast().val;
            }
        }
        return ret;
    }
}
