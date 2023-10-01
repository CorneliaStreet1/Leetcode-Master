package DynamicPlaning;

public class MaxProfit_II_122 {
    public int maxProfit(int[] prices) {
        // dp[i][0] 表示第i天不持有股票能获得的最大利润
        // dp[i][1] 表示第i天持有股票能获得的最大利润
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < dp.length; i ++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[prices.length - 1][0];
    }
}
