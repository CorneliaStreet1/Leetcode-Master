package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class hasPathSum_112 {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return PathSumHelper(root, targetSum);
    }

    public boolean hasPathSum_Recursive(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        return hasPathSum_Recursive(root.left, targetSum - root.val) || hasPathSum_Recursive(root.right, targetSum - root.val);
    }
    public boolean PathSum_LeverOrder(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        Deque<Integer> SumQueue = new ArrayDeque<>();
        nodeQueue.addLast(root);
        SumQueue.addLast(root.val);
        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();
            while (size > 0) {
                TreeNode treeNode = nodeQueue.removeFirst();
                int integer = SumQueue.removeFirst();
                if (treeNode.left == null && treeNode.right == null && integer == targetSum) {
                    return true;
                }
                if (treeNode.left != null) {
                    nodeQueue.addLast(treeNode.left);
                    SumQueue.addLast(treeNode.left.val + integer);
                }
                if (treeNode.right != null) {
                    nodeQueue.addLast(treeNode.right);
                    SumQueue.addLast(treeNode.right.val + integer);
                }
                size --;
            }
        }
        return false;
    }
    public boolean PathSumHelper(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null && targetSum == root.val) {
            return true;
        }
        if (root.left == null && root.right == null && targetSum != 0) {
            return false;
        }
        else {
            boolean b = false,r = false;
            if (root.left != null) {
                b = PathSumHelper(root.left, targetSum - root.val);
            }
            if (root.right != null) {
                r =  PathSumHelper(root.right, targetSum - root.val);
            }
            return b || r;
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1, new TreeNode(2), null);
        System.out.println((new hasPathSum_112()).hasPathSum(treeNode, 1));
    }
}
