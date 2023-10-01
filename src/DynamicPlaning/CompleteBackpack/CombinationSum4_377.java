package DynamicPlaning.CompleteBackpack;

public class CombinationSum4_377 {
    public int combinationSum4(int[] nums, int target) {
        int[][] dp = new int[nums.length + 1][target + 1];

        for (int i = 0; i < dp[0].length; i++) {
            if (i == 0) {
                dp[0][i] = 1;
            }else {
                dp[0][i] = 0;
            }
        }
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 1; j <= target; j++) {
                if (j < nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                }else {
                    dp[i][j] += dp[i][j - nums[i - 1]];
                }
            }
        }
        return dp[nums.length][target];
    }
}
