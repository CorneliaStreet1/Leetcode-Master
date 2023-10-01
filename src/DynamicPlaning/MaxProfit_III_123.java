package DynamicPlaning;

public class MaxProfit_III_123 {
    public int maxProfit(int[] prices) {
        int len = prices.length;
        /*
         * 定义 5 种状态:
         * 0: 没有操作, 1: 第一次买入, 2: 第一次卖出, 3: 第二次买入, 4: 第二次卖出
         */
        int[][] dp = new int[len][5];
        dp[0][1] = -prices[0];
        // 初始化第二次买入的状态是确保 最后结果是最多两次买卖的最大利润
        dp[0][3] = -prices[0];
        for (int i = 1; i < len; i++) {
            // 第一次买入意味着之前是什么都没干的，所以是-prices[i]
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);

            // 第一次卖出:第i天没有操作，沿用前一天卖出股票的状态，即：dp[i][2] = dp[i - 1][2]。
            // 或者前一天是第一次买入, 第i天卖出股票了，那么dp[i][2] = dp[i - 1][1] + prices[i]
            dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1] + prices[i]);

            // 第二次买入: 沿用前一天的第二次买入状态, 或者前一天是第一次卖出,然后今天买入第二次
            dp[i][3] = Math.max(dp[i - 1][3], dp[i - 1][2] - prices[i]);

            // 第二次卖出: 沿用前一天的第二次卖出状态(今天什么都不做), 或者前一天是第二次买入,然后今天卖出第二次。
            dp[i][4] = Math.max(dp[i - 1][4], dp[i - 1][3] + prices[i]);
        }

        return dp[len - 1][4];
    }
}
