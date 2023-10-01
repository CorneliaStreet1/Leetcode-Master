package DynamicPlaning;

public class LongestCommonSubsequence_1143 {
    public int longestCommonSubsequence(String text1, String text2) {
        char[] t2 = text2.toCharArray();
        char[] t1 = text1.toCharArray();
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        dp[0][0] = 0;
        int max = 0;
        for (int i = 1; i < dp.length; i ++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] =  t1[i - 1] == t2[j - 1] ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }
}
