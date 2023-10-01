package Greedy.New;

public class WavingSubsequence_376 {
    public int wiggleMaxLength(int[] nums) {
        if (nums.length == 2 && nums[0] != nums[1]) {
            return 2;
        }
        int PreDiff = 0, CurrDiff = 0, res = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            CurrDiff = nums[i + 1] - nums[i];
            if ((PreDiff >= 0 && CurrDiff < 0) || (PreDiff <= 0 && CurrDiff > 0)) {
                res ++;
                PreDiff = CurrDiff;
            }
        }
        return res;
    }
}
