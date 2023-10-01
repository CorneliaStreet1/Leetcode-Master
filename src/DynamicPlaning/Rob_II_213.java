package DynamicPlaning;

import java.util.Arrays;

public class Rob_II_213 {
    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        return Math.max(RobRange(Arrays.copyOfRange(nums, 0, nums.length - 1)), RobRange(Arrays.copyOfRange(nums, 1, nums.length)));
    }
    private int RobRange(int[] nums) {
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[- 1 + i]);
        }
        return dp[dp.length - 1];
    }

    public static void main(String[] args) {
        (new Rob_II_213()).rob(new int[]{1,2,3,1});
    }
}
