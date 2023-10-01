package DynamicPlaning;

public class Rob_I_198 {
    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        for (int i = 2; i < dp.length; i++) {
            // 偷第i间
            int steal = dp[i - 2] + nums[i - 1];
            // 不偷第i间
            int NotSteal = dp[i - 1];
            dp[i] = Math.max(steal, NotSteal);
        }
        return dp[nums.length];
    }

    public static void main(String[] args) {
        (new Rob_I_198()).rob(new int[]{1,2,3,1});
    }
}
