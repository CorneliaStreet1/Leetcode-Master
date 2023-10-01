package BackTracking.New;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class IntegerCombine_77 {

    List<List<Integer>> Result;
    Deque<Integer> Path;
    public List<List<Integer>> combine(int n, int k) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        BackTracking(1, k, n);
        return Result;
    }

    private void BackTracking(int StartIndex, int k, int n) {
        if (Path.size() == k) {
            Result.add(new ArrayList<>(Path));
            return;
        }
         // 剪枝:n - x + 1 >= k - size(), x <= n + 1 - k + size()
        for (int i = StartIndex; i <= n + 1 - k + Path.size(); i++) {
            Path.addLast(i);
            BackTracking(i + 1, k, n);
            Path.removeLast();
        }
    }

    public static void main(String[] args) {
        (new IntegerCombine_77()).combine(4, 3);
    }
}
