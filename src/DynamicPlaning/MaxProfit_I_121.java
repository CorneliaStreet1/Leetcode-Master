package DynamicPlaning;

public class MaxProfit_I_121 {

    public int maxProfit(int[] prices) {
        // dp[i][0]代表当天不持有股票,dp[i][1]代表当天持有股票
        int[][] dps = new int[prices.length][2];
        dps[0][0] = 0;
        dps[0][1] = -prices[0];
        for (int i = 1; i < dps.length; i ++) {
            dps[i][0] = Math.max(dps[i - 1][0], dps[i - 1][1] + prices[i]);
            dps[i][1] = Math.max(dps[i - 1][1], - prices[i]);
        }
        return dps[prices.length - 1][0];
    }
    public int maxProfit_Greedy(int[] prices) {
        int Low = prices[0], Max = 0;
        for (int price : prices) {
            Low = Math.min(price, Low);
            Max = Math.max(Max, price - Low);
        }
        return Max;
    }
}
