package BackTracking.Old;

import java.util.ArrayList;
import java.util.List;

public class SplitString_131 {

    boolean[][] isPalindrome;
    List<List<String>> result;
    List<String> Path;
    public List<List<String>> partition(String s) {
        result = new ArrayList<>();
        Path = new ArrayList<>();
        BackTracking(s, 0);
        return result;
    }

/*    private void PreProcess(String s) {
        isPalindrome = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            isPalindrome[i][i] = true;
        }
        for (int start = 0; start < s.length(); start++) {
            for (int end = start + 1; end < s.length(); end++) {
                if (s.charAt(start) == s.charAt(end) && isPalindrome_DP(start + 1, end - 1)) {

                }
            }
        }
    }*/
    private boolean isPalindrome_DP(int Start, int End) {
        return isPalindrome[Start][End];
    }
    private void BackTracking(String s, int StartIndex) {
        if (StartIndex >= s.length()) {
            result.add(new ArrayList<>(Path));
            return;
        }
        for (int i = StartIndex; i < s.length(); i ++) {
            String substring = s.substring(StartIndex, i + 1);
            if (isPalindrome(substring)) {
                Path.add(substring);
                BackTracking(s, i + 1);
                Path.remove(Path.size() - 1);
            }
        }
    }
    private boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        int Left = 0, Right = s.length() - 1;
        while (Left <= Right) {
            if ((Right - Left) == 1) {
                if (chars[Left] != chars[Right]) {
                    return false;
                }
            }
            if (chars[Left] != chars[Right]) {
                return false;
            }
            Left ++;
            Right --;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println((new SplitString_131()).partition("aab"));
    }
}
