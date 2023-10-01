package DynamicPlaning;

public class SplitNumber_343 {
    public int integerBreak(int n) {
        int[] dp = new int[n + 1]; // 因为要保存n = 1,0的dp。n >= 2，从0到n总共n + 1个数
        dp[0] = 0; // 0
        dp[1] = 0; // 1
        for (int i = 2; i < dp.length; i++) {
            for (int j = 1; j < i ; j++) {
                int tmp = i - j;
                 // 这里不要写成dp[i] = Math.max(tmp * j, dp[tmp] * j)。还要跟dp当前的值去做比较。
                dp[i] = Math.max(dp[i],Math.max(tmp * j, dp[tmp] * j));
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        (new SplitNumber_343()).integerBreak(10);
    }
}
