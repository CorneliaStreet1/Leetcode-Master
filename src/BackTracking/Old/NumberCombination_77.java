package BackTracking.Old;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class NumberCombination_77 {
    // 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。1 <= k <= n

    Deque<Integer> OneResult;
    List<List<Integer>> ResultSet;
    public List<List<Integer>> combine(int n, int k) {
        OneResult = new ArrayDeque<>();
        ResultSet = new ArrayList<>();
        BackTracking(n, k, 1);
        return ResultSet;
    }
    private void BackTracking(int n, int k, int StartIndex) {
        if (OneResult.size() == k) {
            ResultSet.add(new ArrayList<>(OneResult));
            // OneResult.clear();
            return;
        }
        for (int i = StartIndex; i <= n - (k - OneResult.size()) + 1 ; i++) {
            OneResult.add(i);
            BackTracking(n, k, i + 1);
            OneResult.remove(i);
        }
    }
}
