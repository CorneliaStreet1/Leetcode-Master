package Hashing;

import java.util.LinkedList;

public class ArrayIntersection_349 {
    public int[] intersection(int[] nums1, int[] nums2) {
        boolean[] Occured1 = new boolean[1001];
        boolean[] Occured2 = new boolean[1001];
        LinkedList<Integer> set = new LinkedList<>();
        for (int i : nums1) {
            Occured1[i] = true;
        }
        for (int i : nums2) {
            Occured2[i] = true;
        }
        for (int i = 0; i < Occured1.length; i++) {
            if (Occured1[i] && Occured2[i]) {
                set.addLast(i);
            }
        }
        int[] r = new int[set.size()];
        int i = 0;
        for (Integer integer : set) {
            r[i] = integer;
            i ++;
        }
        return r;
    }
}
