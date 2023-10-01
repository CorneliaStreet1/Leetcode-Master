package BackTracking.Old;

import java.util.*;

public class combinationSum_39 {

    Deque<Integer> Path;
    List<List<Integer>> Result;
    int Sum;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Path = new ArrayDeque<>();
        Result = new ArrayList<>();
        BackTracking(candidates, target, 0);
        RemoveDuplicate(Result);
        return Result;
    }
    private void RemoveDuplicate(List<List<Integer>> list) {
        for (List<Integer> integers : list) {
            integers.sort(Integer::compareTo);
        }
        HashSet<List<Integer>> lists = new HashSet<List<Integer>>();
        lists.addAll(list);
        Result.clear();
        Result.addAll(lists);
    }


    private void BackTracking(int[] candidates, int target, int Start) {
        if (Sum > target) {
            return;
        }
        if (target == Sum) {
            Result.add(new ArrayList<>(Path));
            return;
        }
        for (int i = Start; i < candidates.length; i++) {
            Path.add(candidates[i]);
            Sum += candidates[i];
            BackTracking(candidates, target, i);
            Path.remove(candidates[i]);
            Sum -= candidates[i];
        }
    }

    public static void main(String[] args) {
        System.out.println((new combinationSum_39()).combinationSum(new int[]{2, 3, 6, 7}, 7));
    }
}
