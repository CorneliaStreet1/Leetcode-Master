package DynamicPlaning;

import Public.BinaryTree.TreeNode;

import java.util.HashMap;

public class Rob_III_337 {
    HashMap<TreeNode, Integer> map;
    public int rob(TreeNode root) {
        // map = new HashMap<>();
        // return RobMax(root);
        int[] dp = dp(root);
        return Math.max(dp[0], dp[1]);
    }

    private int[] dp(TreeNode root) {
        if (root == null) {
            return  new int[]{0, 0};
        }
        int[] LeftDP = dp(root.left);
        int[] RightDP = dp(root.right);

        //如果抢劫当前根节点。那就不能抢劫子节点
        int v1 = root.val + LeftDP[0] + RightDP[0];
        // 不抢劫根节点，那就可以去抢劫子节点，取收益最大的选择
        int v2 = Math.max(LeftDP[0], LeftDP[1]) + Math.max(RightDP[0], RightDP[1]);

        // dp[0]是不抢根节点,dp[1]是抢根节点
        return new int[]{v2, v1};
    }

    private int RobMax(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (map.containsKey(root)) {
            return  map.get(root);
        }
        if (root.left == null && root.right == null) {
            return root.val;
        }
        // 如果抢劫根节点,那就不能抢劫左右孩子节点,直接跳过去
        int L = 0, R = 0;
        if (root.left != null) {
            L = RobMax(root.left.left) + RobMax(root.left.right);
        }
        if (root.right != null) {
            R = RobMax(root.right.left) + RobMax(root.right.right);
        }
        int RobRoot = root.val + L + R;

        int NotRob = RobMax(root.left) + RobMax(root.right);
        int max = Math.max(RobRoot, NotRob);
        map.put(root, max);
        return max;
    }
}

