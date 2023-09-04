package StackAndQueue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class topKFrequent_347 {
    public int[] topKFrequent(int[] nums, int k) {
        int[] result = new int[k];
        int index = 0;
        HashMap<Integer, Integer> Num_Frequency = new HashMap<>();
        for (int num : nums) {
            if (!Num_Frequency.containsKey(num)) {
                Num_Frequency.put(num, 1);
            }
            else {
                Num_Frequency.put(num, Num_Frequency.get(num) + 1);
            }
        }
        Object[] objects = Num_Frequency.entrySet().toArray();
        PriorityQueue<Map.Entry<Integer, Integer>> entries = new PriorityQueue<>((o1, o2) -> {
            return -(o1.getValue() - o2.getValue());
        });
        for (Object object : objects) {
            entries.add((Map.Entry<Integer, Integer>) object);
        }
        while (index < k && entries.size() > 0) {
            result[index] = entries.poll().getKey();
            index ++;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString((new topKFrequent_347()).topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));
    }
}
