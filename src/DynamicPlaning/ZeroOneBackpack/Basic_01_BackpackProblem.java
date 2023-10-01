package DynamicPlaning.ZeroOneBackpack;

public class Basic_01_BackpackProblem {
    /*
    * 基本的0-1背包问题
    * 每个物品只有一个
    * 对于一个物品，要么选它，要么不选它。不能选零点几个的它，要么是0个，要么是1个
    * */
    public int ZeroOneBackPack(int BackpackCapacity, int[] Weight, int[] Value) {
        int ItemCount = Weight.length;
        /*
        dp这里列数要+1是因为我们需要考虑背包容量为0的起始情况,
        0到BackpackCapacity总共是BackpackCapacity + 1个数
        */
        int[][] dp = new int[ItemCount][BackpackCapacity + 1];
        for (int i = 0; i < ItemCount; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i < dp[0].length; i++) {
            // 只有物品0,在背包容量为i的情况下,放得下物品0.最大价值就是value[0],否则是0
            dp[0][i] = i >= Weight[0] ? Value[0] : 0;
        }
        for (int i = 1; i < ItemCount; i++) {
            for (int j = 1; j <= BackpackCapacity; j++) {
                if (j < Weight[i]) {
                    // 背包容量j放不下物品i，那么就相当于在物品 0 到物品 i-1 里，背包容量为j的情况下进行选择。
                    dp[i][j] = dp[i - 1][j];;
                }
                else {
                    // 在背包容量为j的情况下,如果选择物品i
                    int ChooseI = dp[i - 1][j - Weight[i]] + Value[i];
                    // 在背包容量为j的情况下,不选择物品i
                    int DoNotChooseI = dp[i - 1][j];
                    dp[i][j] = Math.max(ChooseI, DoNotChooseI);
                }
            }
        }
        return dp[ItemCount - 1][BackpackCapacity];
    }

    public static void main(String[] args) {
        System.out.println((new Basic_01_BackpackProblem()).ZeroOneBackPack(4, new int[]{1, 3, 4}, new int[]{15, 20, 30}));
    }
}
