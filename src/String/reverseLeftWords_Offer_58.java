package String;

public class reverseLeftWords_Offer_58 {//剑指 Offer 58 - II. 左旋转字符串
    public String reverseLeftWords(String s, int n) {
        char[] chars = s.toCharArray();
        reverseString(chars, 0, n - 1);
        reverseString(chars, n, chars.length - 1);
        reverseString(chars, 0, chars.length - 1);
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
}
