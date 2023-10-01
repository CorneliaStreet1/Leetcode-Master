package DynamicPlaning;

import java.util.Arrays;

public class LongestSubsequence_300 {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int Max = 1;
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i ++) {
            for (int k = 0; k < i; k ++) {
                if (nums[i] > nums[k]) {
                    dp[i] = Math.max(dp[i], dp[k] +1);
                    Max = Math.max(dp[i], Max);
                }
            }
        }
        return Max;
    }
}
