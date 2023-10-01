package BackTracking.New;

import java.util.*;

public class Permutations_II_47 {
    List<List<Integer>> Result;
    Deque<Integer> Path;
    boolean[] Used;
    public List<List<Integer>> permuteUnique(int[] nums) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        Used = new boolean[nums.length];
        Arrays.sort(nums);
        BackTracking(nums);
        return Result;
    }
    private void BackTracking(int[] nums) {
        if (Path.size() == nums.length) {
            Result.add(new ArrayList<>(Path));
            return;
        }
        for (int i = 0; i < nums.length; i ++) {
            if (!Used[i]) {
                Used[i] = true;
                if (i > 0 && nums[i - 1] == nums[i] && Used[i - 1] == false && Used[i] == true) {
                    Used[i] = false;
                    continue;
                } else {
                    Path.addLast(nums[i]);
                    BackTracking(nums);
                    Path.removeLast();
                    Used[i] = false;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println((new Permutations_II_47()).permuteUnique(new int[]{1, 1, 2}));
    }
}
