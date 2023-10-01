package Greedy.Old;

public class MaxProfit_II_122 {
    public int maxProfit(int[] prices) {
        int MaxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                MaxProfit += prices[i] - prices[i - 1];
            }
        }
        return MaxProfit;
    }
}
