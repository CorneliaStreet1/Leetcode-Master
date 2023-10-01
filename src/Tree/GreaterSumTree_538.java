package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GreaterSumTree_538 {

    public TreeNode convertBST(TreeNode root) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        getInOrder(root, arrayList);
        HashMap<Integer, Integer> ValToIndex = new HashMap<>();
        int[] list = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            Integer integer = arrayList.get(i);
            ValToIndex.put(integer, i);
            list[i] = integer;
        }
        return buildTree(root, list, ValToIndex);
    }
    private void  getInOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        getInOrder(root.left, list);
        list.add(root.val);
        getInOrder(root.right, list);
    }
    private TreeNode buildTree(TreeNode root, int[] list, HashMap<Integer, Integer> map) {
        if (root == null) {
            return null;
        }
        int Sum = 0;
        for (int i = map.get(root.val); i < list.length; i++) {
            Sum += list[i];
        }
        root.val = Sum;
        root.left = buildTree(root.left, list, map);
        root.right = buildTree(root.right, list, map);
        return root;
    }
}
