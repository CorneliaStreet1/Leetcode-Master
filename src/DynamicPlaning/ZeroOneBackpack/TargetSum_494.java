package DynamicPlaning.ZeroOneBackpack;

public class TargetSum_494 {
    public int findTargetSumWays(int[] nums, int target) {
        /*
        * 把取 - 的全部放一起，记作X。取 + 的全部放一起，记作Y。
        * 也即: target = Y - X
        * 又因为nums数组的总和是一定的,记作Sum
        * 则有 X + Y = Sum
        * 替换掉X或者Y,比如X:target = Y - X = Y - (Sum - Y)
        * Y = (target + Sum) / 2.
        * 因为Y是整数所以target + Sum必须是偶数
        * */
        int Sum = 0;
        for (int num : nums) {
            Sum += num;
        }
        if (Math.abs(target) > Sum) {
            return 0;
        }
        if ((Sum + target) % 2 != 0) {
            return 0;
        }
        /*
        * `dp[i][j]`的含义：从`nums[0:i]`里选取数，把容量为`j`的背包填满，有`dp[i][j]`种方法。
        * */
        int[][] dp = new int[nums.length][(Sum + target) / 2 + 1];
        // 1. 当容量为0时，填满的方法只有唯一一种——什么都不选。
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }
        // 2. 当只有第0个数可以选时，只有`j == num[0]`时方法数为1，其他情况都为0。
        for (int i = 1; i < dp[0].length; i++) {
            dp[0][i] = i == nums[0] ? 1 : 0;
        }
        /*
        3. 考虑`dp[i][j]`：
        1. 如果`j < nums[i]`，选不了`nums[i]`，那么 `dp[i][j] = dp[i - 1][j]`
        2. 如果 `j >= nums[i]`，那么`nums[i]`可选可不选，两种选择加起来，那么 `dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i]]`
        */
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if ( j < nums[i]) {
                    dp[i][j] = dp[i - 1][j];
                }else {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[nums.length - 1][(Sum + target) / 2];
    }

    public static void main(String[] args) {
        (new TargetSum_494()).findTargetSumWays(new int[]{1,1,1,1,1}, 3);
    }
}
