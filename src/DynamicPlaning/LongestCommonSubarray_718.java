package DynamicPlaning;

public class LongestCommonSubarray_718 {
    public int findLength(int[] nums1, int[] nums2) {
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        dp[0][0] = 0;
        int Max = 0;
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums2[j] == nums1[i]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                Max = Math.max(Max, dp[i][j]);
            }
        }
        return Max;
    }
}
