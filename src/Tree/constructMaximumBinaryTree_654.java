package Tree;

import Public.BinaryTree.TreeNode;

public class constructMaximumBinaryTree_654 {
    /*给定一个不重复的整数数组 nums 。 最大二叉树 可以用下面的算法从 nums 递归地构建:
    创建一个根节点，其值为 nums 中的最大值。
    递归地在最大值 左边 的 子数组前缀上 构建左子树。
    递归地在最大值 右边 的 子数组后缀上 构建右子树。
    返回 nums 构建的 最大二叉树 。
    */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTreeHelper(nums, 0, nums.length - 1);
    }
    private TreeNode constructMaximumBinaryTreeHelper(int[] nums, int Start, int End) {
        if (Start > End) {
            return null;
        }
        else {
            int MaxIndex = Start;  //不要写MaxIndex = 0;
            for (int i = Start; i <= End; i++) {
                if (nums[i] > nums[MaxIndex]) {
                    MaxIndex = i;
                }
            }
            TreeNode root = new TreeNode(nums[MaxIndex]);
            root.left = constructMaximumBinaryTreeHelper(nums, Start, MaxIndex - 1);
            root.right = constructMaximumBinaryTreeHelper(nums, MaxIndex + 1, End); //End这里不要写nums.length - 1
            return root;
        }
    }

    public static void main(String[] args) {
        (new constructMaximumBinaryTree_654()).constructMaximumBinaryTree(new int[]{3,2,1,6,0,5});
    }
}
