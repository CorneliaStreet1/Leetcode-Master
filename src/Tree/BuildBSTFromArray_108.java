package Tree;

import Public.BinaryTree.TreeNode;

public class BuildBSTFromArray_108 {
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums, 0, nums.length - 1);
    }

    private TreeNode buildTree(int[] nums,int Star, int End) {
        if (Star > End) {
            return null;
        }
        else {
            int Mid = (Star + End) / 2;
            int rootVal = nums[Mid];
            TreeNode treeNode = new TreeNode(rootVal);
            treeNode.left = buildTree(nums, Star, Mid - 1);
            treeNode.right = buildTree(nums, Mid + 1, End);
            return treeNode;
        }
    }
}
