package Tree;

import Public.BinaryTree.TreeNode;

import java.util.*;

public class findModeInBST_501 {

    HashMap<Integer, Integer> NumberFrequency;

    int base;
    int count;
    int MaxFreq;
    Set<Integer> modes;
    public int[] findMode(TreeNode root) {
        count = 0;
        base = Integer.MAX_VALUE;
        modes = new HashSet<>();
        findModeHelper(root);
        Object[] objects = modes.toArray();
        int[] ret = new int[objects.length];
        for (int i = 0; i < objects.length; i++) {
            ret[i] = (int) objects[i];
        }
        return ret;
    }
    private void findModeHelper(TreeNode root) {
        if (root != null) {
            findModeHelper(root.left);
            if (base == Integer.MAX_VALUE) {
                base = root.val;
            }
            if (root.val == base) {
                count ++;
            }else {
                base = root.val;
                count = 0;
            }
            if (count == MaxFreq) {
                modes.add(root.val);
            }
            if (count > MaxFreq) {
                MaxFreq = count;
                modes.clear();
                modes.add(root.val);
            }
            findModeHelper(root.right);
        }
    }

    private void getValueList(TreeNode root, List<Integer> list) {
        if (root != null) {
            getValueList(root.left, list);
            list.add(root.val);
            getValueList(root.right, list);
        }
    }
    public int[] findMode_Slow(TreeNode root) {
        NumberFrequency = new HashMap<>();
        getFreq(root);
        Object[] objects = NumberFrequency.entrySet().toArray();
        int MaxFreq = 0;
        for (int i = 0; i < objects.length; i++) {
            Map.Entry<Integer, Integer> object = (Map.Entry<Integer, Integer>)objects[i];
            if (object.getValue() > MaxFreq) {
                MaxFreq = object.getValue();
            }
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < objects.length; i++) {
            Map.Entry<Integer, Integer> object = (Map.Entry<Integer, Integer>)objects[i];
            if (object.getValue() == MaxFreq) {
                arrayList.add(object.getKey());
            }
        }
        int[] ret = new int[arrayList.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = arrayList.get(i);
        }
        return ret;
    }
    private void getFreq(TreeNode root) {
        if (root != null) {
            getFreq(root.left);
            if (NumberFrequency.containsKey(root.val)) {
                NumberFrequency.put(root.val, NumberFrequency.get(root.val) + 1);
            }else {
                NumberFrequency.put(root.val, 1);
            }
            getFreq(root.right);
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(
                (new findModeInBST_501()).findMode(
                        new TreeNode(1,
                                new TreeNode(0,new TreeNode(0,new TreeNode(0), null), new TreeNode(0)),
                                new TreeNode(1, new TreeNode(1), new TreeNode(1))))));

    }
}
