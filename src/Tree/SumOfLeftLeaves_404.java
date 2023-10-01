package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class SumOfLeftLeaves_404 {
    public int sumOfLeftLeaves(TreeNode root) {
        LinkedList<Integer> list = new LinkedList<>();
        getLeftLeaves(root, list);
        int sum = 0;
        for (Integer integer : list) {
            sum += integer;
        }
        return sum;
    }
    private void getLeftLeaves(TreeNode root, List<Integer> list) {
        if (root != null) {
            if (root.left != null) {
                if (root.left.left == null && root.left.right == null) {
                    list.add(root.left.val);
                }
            }
            getLeftLeaves(root.left, list);
            getLeftLeaves(root.right, list);
        }
    }
    private void getLeaves_LevelOrder(TreeNode root, List<Integer> list) {
        if (root != null) {
            Deque<TreeNode> nodeQueue = new ArrayDeque<>();
            nodeQueue.addLast(root);
            while (!nodeQueue.isEmpty()) {
                TreeNode treeNode = nodeQueue.removeFirst();
                if (treeNode.left != null){
                    nodeQueue.addLast(treeNode.left);
                    TreeNode left = treeNode.left;
                    if (left.left == null && left.right == null) {
                        list.add(left.val);
                    }
                }
                if (treeNode.right != null) {
                    nodeQueue.addLast(treeNode.right);
                }
            }
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        (new SumOfLeftLeaves_404()).sumOfLeftLeaves(treeNode);
    }
}
