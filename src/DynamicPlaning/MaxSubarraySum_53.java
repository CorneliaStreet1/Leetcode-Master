package DynamicPlaning;

public class MaxSubarraySum_53 {
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dp.length; i++) {
            dp[i] = nums[i];
            max = Math.max(max, dp[i]);
        }
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
