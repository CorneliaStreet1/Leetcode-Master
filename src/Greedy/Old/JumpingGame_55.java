package Greedy.Old;

public class JumpingGame_55 {
    public boolean canJump(int[] nums) {
        int Cover = nums[0];
        for (int i = 0; i <= Cover; i++) {  // i 每次移动只能在 cover 的范围内移动
            if (i + nums[i] > Cover) {
                Cover = i + nums[i];
            }
            if (Cover >= nums.length - 1) {
                return true; // 如果 cover 大于等于了终点下标，直接 return true 就可以了。
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println((new JumpingGame_55()).canJump(new int[]{0}));
    }
}
