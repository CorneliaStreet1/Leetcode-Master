package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Iterative_Postorder_145 {
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }
        Deque<Integer> arrayList = new ArrayDeque<>();
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        Deque<TreeNode> treeNodes = new ArrayDeque<>();
        treeNodes.addLast(root);
        while (!treeNodes.isEmpty()) {
            TreeNode treeNode = treeNodes.removeLast();
            if (treeNode.left != null) {
                treeNodes.addLast(treeNode.left);
            }
            if (treeNode.right != null) {
                treeNodes.addLast(treeNode.right);
            }
            arrayList.addLast(treeNode.val);
        }
        while (! arrayList.isEmpty()) {
            arrayList1.add(arrayList.removeLast());
        }
        return arrayList1;
    }
}
