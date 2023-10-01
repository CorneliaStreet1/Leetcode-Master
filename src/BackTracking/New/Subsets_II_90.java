package BackTracking.New;

import java.util.*;

public class Subsets_II_90 {
    List<List<Integer>> Result;
    boolean[] Used;
    Deque<Integer> Path;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Used = new boolean[nums.length];
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int num : nums) {
            arrayList.add(num);
        }
        Result.add(arrayList);
        Arrays.sort(nums);
        // 逐个列举子集中的元素个数，按照元素个数求子集
        for (int SubSetElementCount = 0; SubSetElementCount < nums.length; SubSetElementCount ++) {
            BackTracking(nums, SubSetElementCount, 0);
        }
        return  Result;
    }
    private void BackTracking(int[] nums, int ElementCount, int StartIndex) {
        if (Path.size() == ElementCount) {
            Result.add(new ArrayList<>(Path));
        }
        for (int i = StartIndex; i < nums.length; i ++) {
            Used[i] = true;
            if (i > 0 && nums[i - 1] == nums[i] && !Used[i - 1] && Used[i]) {
                Used[i] = false;
                continue;
            }else {
                Path.addLast(nums[i]);
                BackTracking(nums, ElementCount, i + 1);
                Path.removeLast();
                Used[i] = false;
            }
        }
    }
}
