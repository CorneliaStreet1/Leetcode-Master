package DynamicPlaning.CompleteBackpack;

public class Basic_CompleteBackpack {
    public int CompleteBackpack(int capacity, int[] weight, int[] value) {
        int[][] dp = new int[weight.length + 1][capacity + 1];

        // 如果是只有0件物品可以选，那么最大价值肯定是0
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }
        // 如果背包的容量为0，那么最大价值肯定也是0
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }

        // 接下来考虑在前i种物品里选择的时候，背包容量为j > 0的时候，最大价值是多少(物品从1开始编号)。

        for (int item = 1; item <= weight.length; item ++) {
            for (int capa = 0; capa <= capacity ; capa++) {
                if (capa < weight[item - 1]) { // 这里稍微注意一下就是, 第i个物品的重量和价值的索引是i - 1.
                    // 容量太小放不下哪怕一件第item种物品,那就不放
                    dp[item][capa] = dp[item - 1][capa];
                }else {
                    // 从不放,和放多件里选收益更大的那个.因为同一件物品可以放多个，所以是dp[item][capa - weight[item]]
                    dp[item][capa] = Math.max(dp[item - 1][capa], dp[item][capa - weight[item - 1]] + value[item - 1]);
                }
            }
        }
        return dp[weight.length][capacity];
    }

    public static void main(String[] args) {
        System.out.println((new Basic_CompleteBackpack()).CompleteBackpack(4, new int[]{1, 3, 4}, new int[]{15, 20, 30}));
    }
}
