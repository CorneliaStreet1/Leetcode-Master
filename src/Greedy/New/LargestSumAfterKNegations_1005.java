package Greedy.New;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LargestSumAfterKNegations_1005 {
    public int largestSumAfterKNegations(int[] nums, int k) {
        ArrayList<Integer> Negative = new ArrayList<>();
        ArrayList<Integer> NonNegative = new ArrayList<>();
        for (int num : nums) {
            if (num < 0) {
                Negative.add(num);
            }
            else {
                NonNegative.add(num);
            }
        }
        Negative.sort((o1, o2) -> o1 - o2);
        NonNegative.sort(Comparator.comparingInt(Integer::intValue));
        if (k < Negative.size()) {
            int Sum = 0;
            for (Integer integer : Negative) {
                if (k > 0) {
                    Sum += - integer;
                    k --;
                }
                else {
                    Sum += integer;
                }
            }
            for (Integer integer : NonNegative) {
                Sum += integer;
            }
            return Sum;
        }else {
            int Sum = 0;
            for (Integer integer : Negative) {
                    Sum += - integer;
                    k --;
            }
            if (k % 2 == 0) {
                for (Integer integer : NonNegative) {
                    Sum += integer;
                }
            }else {
                for (Integer integer : NonNegative) {
                    Sum += integer;
                }
                if (Negative.isEmpty()) {
                    Sum -= 2 * NonNegative.get(0);
                }
                else if(NonNegative.isEmpty()){
                    Sum -= 2 * -Negative.get(Negative.size() - 1);
                }
                else {
                    Sum -= 2 * Math.min(NonNegative.get(0), -Negative.get(Negative.size() - 1));
                }
            }
            return Sum;
        }
    }

    public static void main(String[] args) {
        (new LargestSumAfterKNegations_1005()).largestSumAfterKNegations(new int[] {-8,3,-5,-3,-5,-2}, 6);
    }
}
