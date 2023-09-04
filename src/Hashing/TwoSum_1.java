package Hashing;

import java.util.HashMap;
import java.util.HashSet;

public class TwoSum_1 {
    public int[] twoSum(int[] nums, int target) {
        int[] ret = new int[2];
        HashMap<Integer,Integer> Map = new HashMap<>();
        for (int i = 0; i < nums.length; i ++) {
            if (!Map.containsKey(target - nums[i])) {
                Map.put(nums[i], i);
            }
            else {
                ret[0] = i;
                ret[1] = Map.get(target - nums[i]);
                break;
            }
        }
        return ret;
    }
}
