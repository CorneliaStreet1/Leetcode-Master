package BackTracking.New;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LetterCombinationsOfPhoneNumber_17 {
    List<String> Result;
    StringBuilder Path;
    HashMap<Integer, char[]> PhoneNumberChars;
    public List<String> letterCombinations(String digits) {
        Path = new StringBuilder();
        Result = new ArrayList<>();
        if (digits == null || digits.equals("")) {
            return Result;
        }
        PhoneNumberChars = new HashMap<>();
        char[][] chars = new char[][] {
                {'a', 'b', 'c'},
                {'d', 'e', 'f'},
                {'g', 'h', 'i'},
                {'j', 'k', 'l'},
                {'m', 'n', 'o'},
                {'p', 'q', 'r', 's'},
                {'t', 'u', 'v'},
                {'w', 'x', 'y', 'z'},
        };
        for (int i = 2; i < 10; i++) {
            PhoneNumberChars.put(i, chars[i - 2]);
        }
        BackTracking(digits, 0);
        return Result;
    }
    private void BackTracking(String s, int index) {
        if (Path.length() == s.length()) {
            Result.add(Path.toString());
            return;
        }
        char c = s.charAt(index);
        char[] chars = PhoneNumberChars.get(Integer.parseInt(String.valueOf(c)));
        for (int i = 0; i < chars.length; i++) {
            Path.append(chars[i]);
            BackTracking(s, index + 1);
            Path.delete(Path.length() - 1, Path.length());
        }
    }
}
