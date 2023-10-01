package DynamicPlaning;

public class PalindromicSubstring_647 {
    public int countSubstrings(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        int ret = 0;
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = i == 0;
        }
        for (int i = dp.length - 1; i>= 0; i--) {
            for (int j = 0; j < dp[0].length; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (i == j) {
                        dp[i][j] = true;
                        ret ++;
                    }
                    else if (j - i == 1) {
                        dp[i][j] = true;
                        ret ++;
                    }else {
                        dp[i][j] = dp[i + 1][j - 1];
                        ret = (dp[i][j]) ? ret + 1 : ret;
                    }
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        (new PalindromicSubstring_647()).countSubstrings("aaa");
    }
}
