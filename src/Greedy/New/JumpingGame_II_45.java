package Greedy.New;

import java.util.Arrays;

public class JumpingGame_II_45 {
    public int jump(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int jump = 1, NextMaxCover = nums[0], MaxCover = nums[0];
        for (int i = 0; i <= MaxCover; i ++) {
            NextMaxCover = Math.max(NextMaxCover, i + nums[i]);
            if (MaxCover >= nums.length - 1) {
                break;
            }
            if (i == MaxCover) {
                MaxCover = NextMaxCover;
                jump ++;
            }
        }
        return jump;
    }
    public int jump_DP(int[] nums) {
        // dp[i] 代表从0跳转到i的最小跳跃次数
        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // 对于dp[i]: 从 0 到 i - 1的位置依次尝试跳过来。
        // 比如从0 <= j <= i - 1跳过来,那么跳跃次数就是dp[j] + 1.遍历j取能到达i的最小的dp[j] + 1
        for (int i = 1; i < dp.length; i ++) {
            for (int j = 0; j < i; j ++) {
                if (j + nums[j] >= i) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[nums.length - 1];
    }

    public static void main(String[] args) {
        (new JumpingGame_II_45()).jump(new int[]{1,2,1,1,1});
    }
}
