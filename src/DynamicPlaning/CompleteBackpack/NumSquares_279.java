package DynamicPlaning.CompleteBackpack;

import java.util.Arrays;

public class NumSquares_279 {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i ++) {
            for (int j = 1; j * j <= i; j ++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println((new NumSquares_279()).numSquares(12));
    }
}
