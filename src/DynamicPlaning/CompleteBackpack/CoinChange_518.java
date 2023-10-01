package DynamicPlaning.CompleteBackpack;

public class CoinChange_518 {
    public int change(int amount, int[] coins) {
        int[][] dp = new int[coins.length + 1][amount + 1];

        // 如果总金额为0.那么方案只有1种
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }
        // 如果只能从前0种硬币里选择,那么除了金额为0的有1种方案, 方案只有0种。
        for (int i = 1; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }

        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount ; j++) {
                if (j < coins[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                }else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                }
            }
        }
        return dp[coins.length][amount];
    }
}
