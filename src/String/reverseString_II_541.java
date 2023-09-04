package String;

import java.util.Arrays;

public class reverseString_II_541 {
    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        //完整的2k的个数
        int CompleteReverse = chars.length / (2 * k);
        //余数
        int Remaining = chars.length % (2 * k);
        for (int i = 0; i < CompleteReverse; i++) {
            int Start = i * 2 * k;
            reverseString(chars, Start, Start + k - 1);
        }
        if (Remaining < k && Remaining > 0) {
            reverseString(chars, chars.length - Remaining, chars.length - 1);
        }
        else if (Remaining >= k && Remaining < 2 * k){
            reverseString(chars, CompleteReverse * 2 * k, CompleteReverse * 2 * k + k - 1);
        }
        return String.valueOf(chars);
    }
    private void reverseString(char[] s, int Start, int End) {
        int L = Start, R = End;
        while (L != R) {
            if ((R - L) == 1) {
                char tmp = s[L];
                s[L] = s[R];
                s[R] = tmp;
                break;
            }
            char tmp = s[L];
            s[L] = s[R];
            s[R] = tmp;
            L ++;
            R --;
        }
    }

    public static void main(String[] args) {
        System.out.println((new reverseString_II_541()).reverseStr("abcd", 2));
    }
}
