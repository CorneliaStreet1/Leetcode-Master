package BackTracking.Old;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class NumberCombinationSum3_216 {

    List<List<Integer>> Result;
    Deque<Integer> Path;
    int Sum;
    public List<List<Integer>> combinationSum3(int k, int n) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        BackTracking(k, n, 1);
        return Result;
    }
    public void BackTracking(int k, int n, int StartValue) {
        if (Sum > n) {
            return;
        }
        if (Path.size() > k) {
            return;
        }
        if (Path.size() == k && Sum == n) {
            Result.add(new ArrayList<>(Path));
            //Sum = 0;
            return;
        }
        for (int i = StartValue; i < 10; i++) {
            Sum += i;
            Path.add(i);
            BackTracking(k, n, i + 1);
            Path.remove(i);
            Sum -= i;
        }
    }
}
