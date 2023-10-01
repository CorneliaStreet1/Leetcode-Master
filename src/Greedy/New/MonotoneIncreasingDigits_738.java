package Greedy.New;

public class MonotoneIncreasingDigits_738 {
    public int monotoneIncreasingDigits(int n) {
        char[] chars = String.valueOf(n).toCharArray();
        int Start = chars.length;
        for (int i = chars.length - 2; i >= 0; i--) {
            Integer integer = Integer.valueOf(String.valueOf(chars[i]));
            Integer integer1 = Integer.valueOf(String.valueOf(chars[i + 1]));
            if (integer > integer1) {
                chars[i] = Character.forDigit(integer - 1, 10);
                chars[i + 1] = '9';
                Start = i + 1;
            }
        }
        for (int i = Start; i < chars.length; i++) {
            chars[i] = '9';
        }
        return Integer.parseInt(String.valueOf(chars));
    }

    public static void main(String[] args) {
        (new MonotoneIncreasingDigits_738()).monotoneIncreasingDigits(100);
    }
}
