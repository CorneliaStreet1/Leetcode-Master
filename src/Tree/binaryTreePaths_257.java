package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class binaryTreePaths_257 {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        getPaths(root, result, "");
        return result;
    }
    private void getPaths(TreeNode root, List<String> result, String Trace) {
        if (root != null) {
            StringBuilder stringBuilder = new StringBuilder(Trace);
            stringBuilder.append(root.val);
            if (root.left == null && root.right == null) {
                result.add(stringBuilder.toString());
            }
            else {
                stringBuilder.append("->");
                getPaths(root.left, result, stringBuilder.toString());
                getPaths(root.right, result, stringBuilder.toString());
            }
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1, new TreeNode(2, null, new TreeNode(5)), new TreeNode(3));
        System.out.println((new binaryTreePaths_257()).binaryTreePaths(treeNode));
    }
}
