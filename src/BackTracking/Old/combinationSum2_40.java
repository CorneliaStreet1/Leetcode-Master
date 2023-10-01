package BackTracking.Old;

import java.util.*;

public class combinationSum2_40 {

    Deque<Integer> Path;
    List<List<Integer>> Result;
    int Sum;
    boolean[] Used;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Path = new ArrayDeque<>();
        Result = new ArrayList<>();
        Used = new boolean[candidates.length];
        Sum = 0;
        Arrays.sort(candidates);
        BackTracking(candidates, target, 0);
        return Result;
    }
    private void BackTracking(int[] candidates, int target, int StartIndex) {
        if (Sum > target) {
            return;
        }
        if (Sum == target) {
            ArrayList<Integer> arrayList = new ArrayList<>(Path);
            Result.add(arrayList);
            return;
        }
        for (int i = StartIndex; i < candidates.length; i++) {
            if (i > 0 && candidates[i] == candidates[ i - 1] && Used[i - 1] == false) {
                continue;
            }
            Path.add(candidates[i]);
            Used[i] = true;
            Sum += candidates[i];
            BackTracking(candidates, target, i + 1);
            Path.remove(candidates[i]);
            Used[i] = false;
            Sum -= candidates[i];
        }
    }

    public static void main(String[] args) {
        System.out.println((new combinationSum2_40()).combinationSum2(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, 30));
    }
}
