package String;

public class KMP_strStr_28 {
    public int strStr(String s, String Patten) {
        if (Patten == null || s == null) {
            return -1;
        }
        if ("".equals(Patten)) {
            return 0;
        }
        int[] Next = getNext(Patten);
        int i = 0, j = 0;
        char[] chars = s.toCharArray();
        char[] chars1 = Patten.toCharArray();
        while (i < s.length() && j < Patten.length()) {
            if (chars[i] == chars1[j]) {
                i ++;
                j ++;
            }else {
                if (j == 0) {
                    j = 0;
                    i ++;
                }
                else {
                    j = Next[j - 1];
                }
            }
        }
        if (j == Patten.length()) {
            return i-j;
        }
        return -1;
    }
    private int[] getNext(String Patten) {
        int[] Next = new int[Patten.length()];
        char[] chars = Patten.toCharArray();
        Next[0] = 0;
        for (int i = 1; i < Next.length; i++) {
            int n = Next[i - 1];
            if (chars[i] == chars[n]) {
                Next[i] = n + 1;
            }else {
                while (n > 0 && chars[i] != chars[n]) {
                    n = Next[n - 1];
                }
                Next[i] = n;
            }
        }
        return Next;
    }

    private int[] getNext_BF(String Patten) {
        int[] Next = new int[Patten.length()];
        for (int i = 0; i < Next.length; i++) {
            if (i == 0) {
                Next[0] = 0;
            }
            else {
                for (int j = 0; j < i; j++) {
                    CharSequence charSequence = Patten.subSequence(0, j + 1);
                    String substring = Patten.substring(i - j, i + 1);
                    if (charSequence.equals(substring)) {
                        Next[i] = j + 1;
                    }
                }
            }
        }
        return Next;
    }

    public static void main(String[] args) {
        System.out.println((new KMP_strStr_28()).getNext("aabaaac"));
    }
}
