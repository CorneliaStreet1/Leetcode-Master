package DynamicPlaning;

import java.util.Arrays;

public class LongestContinuousSubsequence_674 {
    public int findLengthOfLCIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = nums[i] > nums[i - 1] ? dp[i - 1] + 1 : dp[i];
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
