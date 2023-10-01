package BackTracking.New;

import java.util.*;

public class combinationSum_39 {
    List<List<Integer>> Result;
    Deque<Integer> Path;
    //HashSet<List<Integer>> set;
    int Sum;
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        //set = new HashSet<>();
        Sum = 0;
        BackTracking(candidates, target, 0);
        return  Result;
    }
    private void BackTracking(int[] candidates, int target, int StartIndex) {
        if (Sum > target) {
            return;
        }
        if (Sum == target) {
            ArrayList<Integer> arrayList = new ArrayList<>(Path);
            Result.add(arrayList);
/*            arrayList.sort(Integer::compareTo);
            if (set.add(arrayList)) {
                Result.add(arrayList);
            }*/
        }
        for (int i = StartIndex; i < candidates.length; i++) {
            Path.addLast(candidates[i]);
            Sum += candidates[i];
            BackTracking(candidates, target, i);
            Path.removeLast();
            Sum -= candidates[i];
        }
    }
}
