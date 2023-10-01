package DynamicPlaning.ZeroOneBackpack;

public class lastStoneWeightII_1049 {
    public int lastStoneWeightII(int[] stones) {
        int Sum = 0;
        for (int stone : stones) {
            Sum += stone;
        }
        int backpack = Backpack(Sum / 2, stones, stones);
        return Sum  - 2 * backpack;
    }
    private int Backpack(int capacity, int[] weight, int[] value) {
        int[][] dp = new int[weight.length][capacity + 1];
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = i >= weight[0] ? value[0] : 0;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < capacity + 1; j++) {
                if (j < weight[i]) {
                    dp[i][j] = dp[i - 1][j];
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
            }
        }
        return dp[weight.length - 1][capacity];
    }

    public static void main(String[] args) {
        (new lastStoneWeightII_1049()).lastStoneWeightII(new int[]{2,7,4,1,8,1});
    }
}
