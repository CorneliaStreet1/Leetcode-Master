package Greedy.Old;

public class WavingSubsequence_376 {
    public int wiggleMaxLength(int[] nums) {
        int PreDiff = 0, PostDiff;
        if (nums.length == 2 && nums[0] != nums[1]) {
            return 2;
        }
        int Result = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            PostDiff = nums[i + 1] - nums[i];
            if ((PreDiff <= 0 && PostDiff > 0) || (PreDiff >= 0 && PostDiff < 0)) {
                Result ++;
                PreDiff = PostDiff;
            }
        }
        return Result + 1;
    }
}
