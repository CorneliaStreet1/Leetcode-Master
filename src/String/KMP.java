package String;

public class KMP {
    private int[] Next;
    private String Patten;
    public KMP(String Patten) {
        this.Patten = Patten;
        Next = new int[Patten.length()];
        this.getNext();
    }
    private void getNext() {
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
    }
    public int KMP(String s) {
        int i = 0, j = 0;
        char[] chars = s.toCharArray();
        char[] chars1 = Patten.toCharArray();
        while (i < s.length() && j < Patten.length()) {
            if (chars[i] == chars1[j]) {
                i ++;
                j ++;
            }else {
                j = Next[j - 1];
            }
        }
        if (j == Patten.length()) {
            return i-j;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println((new KMP("aaaabc")).KMP("aaaaaaabc"));
    }
}
