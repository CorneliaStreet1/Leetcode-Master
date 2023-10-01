package BackTracking.New;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class NumberCombinationSum3_39 {
    int Sum;
    List<List<Integer>> Result;
    Deque<Integer> Path;
    public List<List<Integer>> combinationSum3(int k, int n) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        Sum = 0;
        BackTracking(k, n, 1);
        return Result;
    }
    private void BackTracking(int k, int n, int StartPos) {
        if (Sum > n) {
            return;
        }
        if (Path.size() == k) {
            if (Sum == n) {
                Result.add(new ArrayList<>(Path));
            }
            return;
        }
        for (int i = StartPos; i <= 9 ; i++) {
            Sum += i;
            Path.addLast(i);
            BackTracking(k, n, i + 1);
            Sum -= i;
            Path.removeLast();
        }
    }

    public static void main(String[] args) {
        System.out.println((new NumberCombinationSum3_39()).combinationSum3(3, 7));
    }
}
