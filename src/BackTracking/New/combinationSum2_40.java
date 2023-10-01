package BackTracking.New;

import java.util.*;

public class combinationSum2_40 {

    List<List<Integer>> Result;
    Deque<Integer> Path;
    boolean[] Used;
    int Sum;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        Used = new boolean[candidates.length];
        Arrays.sort(candidates);
        Arrays.fill(Used, false);
        BackTracking(candidates, target, 0);
        return Result;
    }

    private void  BackTracking(int[] candidates, int target, int StartIndex) {
        if (Sum > target) {
            return;
        }
        if (Sum == target) {
            Result.add(new ArrayList<>(Path));
            return;
        }
        for (int i = StartIndex; i < candidates.length; i ++) {
            Used[i] = true;
            if (i > 0 && candidates[i - 1] == candidates[i] && !Used[i - 1] && Used[i]) {
                Used[i] = false;
                continue;
            }else {
                Sum += candidates[i];

                Path.addLast(candidates[i]);
                BackTracking(candidates, target, i + 1);
                Sum -= candidates[i];
                Used[i] = false;
                Path.removeLast();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println((new combinationSum2_40()).combinationSum2(new int[]{1, 2, 1}, 3));
    }
}
