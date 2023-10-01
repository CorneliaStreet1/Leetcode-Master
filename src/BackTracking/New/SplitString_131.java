package BackTracking.New;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SplitString_131 {
    List<List<String>> Result;
    Deque<String> Path;

    public List<List<String>> partition(String s) {
        Result = new ArrayList<>();
        Path = new ArrayDeque<>();
        BackTracking(s, 0);
        return Result;
    }
    private void BackTracking(String s, int StartIndex) {
        if (StartIndex >= s.length()) {
            Result.add(new ArrayList<>(Path));
            return;
        }
        for (int i = StartIndex; i < s.length(); i ++) {
            String substring = s.substring(StartIndex, i + 1);
            if (isPalindrome(substring)) {
                Path.addLast(substring);
                BackTracking(s, i  + 1);
                Path.removeLast();
            }else {
                continue;
            }
        }
    }
    private boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        int L = 0, R = chars.length - 1;
        while (L <= R) {
            if (R - L == 1) {
                if (chars[L] != chars[R]) {
                    return false;
                }
            }
            if (chars[L] != chars[R]) {
                return false;
            }
            L ++;
            R --;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println((new SplitString_131()).partition("abcd"));
    }
}
