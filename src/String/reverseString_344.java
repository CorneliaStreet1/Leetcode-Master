package String;

public class reverseString_344 {
    public void reverseString(char[] s) {
        int L = 0, R = s.length - 1;
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
        (new reverseString_344()).reverseString("abcde".toCharArray());
    }
}
