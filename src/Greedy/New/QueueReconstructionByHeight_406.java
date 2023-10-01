package Greedy.New;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class QueueReconstructionByHeight_406 {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o2[0] - o1[0];
            }else {
                return o1[1] - o2[1];
            }
        });
        LinkedList<int[]> Queue = new LinkedList<>();
        for (int[] ints : people) {
            Queue.add(ints[1], ints);
        }
        return Queue.toArray(new int[people.length][2]);
    }
}
