package Greedy.New;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MergeIntervals_56 {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, ((o1, o2) -> {
            return o1[0] - o2[0];
        }));
        ArrayList<int[]> ans = new ArrayList<>();
        int S = intervals[0][0], E = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > E) {
                ans.add(new int[]{S, E});
                S = intervals[i][0];
                E = intervals[i][1];
            }else {
                E = Math.max(E, intervals[i][1]);
            }
        }
        ans.add(new int[]{S, E});
        return ans.toArray(new int[ans.size()][2]);
    }

    public static void main(String[] args) {
        (new MergeIntervals_56()).merge(new int[][]{
                {1, 3},
                {2, 6},
                {8, 10},
                {15, 18}
        });
    }
}
