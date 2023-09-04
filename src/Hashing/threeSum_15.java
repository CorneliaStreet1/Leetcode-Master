package Hashing;

import java.util.Arrays;
import java.util.List;

public class threeSum_15 {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int j = i + 1, k = nums.length - 1;
            int Sum = nums[i] + nums[k] + nums[j];

        }
        return null;
    }
}
