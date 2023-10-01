package BackTracking.New;

import java.util.*;

public class Subsets_78 {

    List<List<Integer>> Result;
    Deque<Integer> Path;
    public List<List<Integer>> subsets(int[] nums) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int num : nums) {
            arrayList.add(num);
        }
        Result.add(arrayList);
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
            Path.addLast(nums[i]);
            BackTracking(nums, ElementCount, i + 1);
            Path.removeLast();
        }
    }
}
