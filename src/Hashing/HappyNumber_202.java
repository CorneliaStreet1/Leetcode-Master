package Hashing;

import java.util.HashSet;

public class HappyNumber_202 {
    public boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet<>();
        while (true) {
            n = getSum(n);
            if (!set.add(n)) {
                return false;
            }
            if (n == 1) {
                return true;
            }
        }
    }
    private int getSum(int n) {
        int Sum = 0;
        while (n != 0) {
            Sum += (n % 10) * (n % 10);
            n = n / 10;
        }
        return Sum;
    }
}
