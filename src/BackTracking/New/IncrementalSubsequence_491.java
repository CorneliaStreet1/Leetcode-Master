package BackTracking.New;

import java.util.*;

public class IncrementalSubsequence_491 {
    List<List<Integer>> Result;
    Deque<Integer> Path;

    HashSet<List<Integer>> Set;
    public List<List<Integer>> findSubsequences(int[] nums) {
        Result = new ArrayList<>();
        Set = new HashSet<>();
        Path = new ArrayDeque<>();
        for (int i = 2; i <= nums.length; i ++) {
            BackTracking(nums, i, 0);
        }
        return Result;
    }

    private void BackTracking(int[] Nums, int ElementCount, int StartIndex) {
        if (Path.size() == ElementCount) {
            if (Set.add(new ArrayList<>(Path))) {
                Result.add(new ArrayList<>(Path));
            }
        }
        for (int i = StartIndex; i < Nums.length; i ++) {
            if (Path.isEmpty() || Nums[i] >= Path.getLast()) {
                Path.addLast(Nums[i]);
                BackTracking(Nums, ElementCount, i + 1);
                Path.removeLast();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println((new IncrementalSubsequence_491()).findSubsequences(new int[]{4, 6, 7, 7}));
    }
}
