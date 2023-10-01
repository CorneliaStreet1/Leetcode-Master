package DynamicPlaning;

public class DifferentSubsequence_115 {
    public int numDistinct(String s, String t) {
        /*
        * 因为 dp[i][j]表示的是i-1和j-1的次数。
        * 所以dp[s.length() + 1][t.length() + 1]才是表示了s的子序列中t出现的次数
        * 所以这里不是 new dp[s.length()][t.length()]
        * */
        int[][] dp = new int[s.length() + 1][t.length() + 1];
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }
        dp[0][0] = 1;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = (s.charAt(i - 1) == t.charAt(j - 1)) ? dp[i - 1][j - 1] + dp[i - 1][j] : dp[i - 1][j];
            }
        }
        return dp[s.length()][t.length()];
    }
}
