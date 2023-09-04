package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class rightSideView_199 {
    public List<Integer> rightSideView(TreeNode root) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (root == null) {
            return arrayList;
        }
        Deque<TreeNode> NodeQueue = new ArrayDeque<>();
        NodeQueue.addLast(root);
        while (!NodeQueue.isEmpty()) {
            int size = NodeQueue.size();
            while (size > 0) {
                TreeNode treeNode = NodeQueue.removeFirst();
                if (treeNode.left != null) {
                    NodeQueue.addLast(treeNode.left);
                }
                if (treeNode.right != null) {
                    NodeQueue.addLast(treeNode.right);
                }
                if (size == 1) {
                    arrayList.add(treeNode.val);
                }
                size --;
            }
        }
        return arrayList;
    }
}
