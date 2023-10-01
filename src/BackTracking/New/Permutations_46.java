package BackTracking.New;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Permutations_46 {
    List<List<Integer>> Result;
    Deque<Integer> Path;
    boolean[] Used;
    public List<List<Integer>> permute(int[] nums) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        Used = new boolean[nums.length];
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
                Path.addLast(nums[i]);
                BackTracking(nums);
                Path.removeLast();
                Used[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println((new Permutations_46()).permute(new int[]{1, 2, 3}));
    }
}
