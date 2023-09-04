package Hashing;

import java.util.HashMap;

public class fourSumCount_II_454 {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : nums1) {
            for (int i1 : nums2) {
                if (!map.containsKey(i + i1)) {
                    map.put(i + i1, 1);
                }
                else {
                    map.put(i + i1, map.get(i + i1) + 1);
                }
            }
        }
        int count = 0;
        for (int i : nums3) {
            for (int i1 : nums4) {
                if (map.containsKey(-(i + i1))) {
                    count += map.get(-(i + i1));
                }
            }
        }
        return count;
    }
}
