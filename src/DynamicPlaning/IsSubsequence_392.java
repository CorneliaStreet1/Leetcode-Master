package DynamicPlaning;

public class IsSubsequence_392 {

    public boolean isSubsequence(String s, String t) {
        char[] chars = s.toCharArray();
        int j = 0;
        char[] chars1 = t.toCharArray();
        for (int i = 0; i < chars1.length && j < chars.length; i ++) {
            if (chars1[i] == chars[j]) {
                j ++;
            }
        }
        return j == chars.length;
    }
    public boolean isSubsequence_DP(String s, String t) {
        char[] t2 = t.toCharArray();
        char[] t1 = s.toCharArray();
        int[][] dp = new int[s.length() + 1][t.length() + 1];
        dp[0][0] = 0;
        int max = 0;
        for (int i = 1; i < dp.length; i ++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] =  t1[i - 1] == t2[j - 1] ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
                max = Math.max(max, dp[i][j]);
            }
        }
        return max == s.length();
    }

    public static void main(String[] args) {
        (new IsSubsequence_392()).isSubsequence("b", "ahbgdc");
    }
}
