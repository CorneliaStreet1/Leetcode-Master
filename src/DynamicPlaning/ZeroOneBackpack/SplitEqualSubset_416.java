package DynamicPlaning.ZeroOneBackpack;

public class SplitEqualSubset_416 {
    public boolean canPartition(int[] nums) {
        int Sum = 0;
        for (int num : nums) {
            Sum += num;
        }
        if (Sum % 2 != 0) {
            return false;
        }
        int i = BackPacking(Sum / 2, nums, nums);
        return (Sum / 2) == i;
    }
    private int BackPacking(int capacity, int[] weight, int[] value) {
        int[][] dp = new int[weight.length][(capacity + 1)];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = i >= weight[0] ? value[0] : 0;
        }
        for (int Item = 1; Item < dp.length; Item++) {
            for (int capa = 1; capa <= capacity ; capa++) {
                if (capa < weight[Item]) {
                    dp[Item][capa] = dp[Item - 1][capa];
                }else {
                     // 这里永远不要忘记给dp[Item - 1][capa - weight[Item]]再加上一个value[Item]。不要忘记把物品Item本身的价值加上
                    dp[Item][capa] = Math.max(dp[Item - 1][capa], dp[Item - 1][capa - weight[Item]] + value[Item]);
                }
            }
        }
        return dp[weight.length - 1][capacity];
    }

    public static void main(String[] args) {
        (new SplitEqualSubset_416()).canPartition(new  int[] {1,2,3,5});
    }
}
