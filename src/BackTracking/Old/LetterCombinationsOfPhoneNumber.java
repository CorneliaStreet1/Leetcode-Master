package BackTracking.Old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterCombinationsOfPhoneNumber {

    List<String> Result;
    StringBuilder Path;
    Map<Integer, char[]> NumberToChar;
    public List<String> letterCombinations(String digits) {
        if (digits == null) {
            return null;
        }
        if (digits.equals("")) {
            return new ArrayList<String>();
        }
        NumberToChar = new HashMap<>();
        char[][] chars = new char[][]{
                {'a', 'b', 'c'}, // 2
                {'d', 'e', 'f'}, // 3
                {'g', 'h', 'i'}, // 4
                {'j', 'k', 'l'}, // 5
                {'m', 'n', 'o'}, // 6
                {'p', 'q', 'r', 's'}, // 7
                {'t', 'u', 'v'}, // 8
                {'w', 'x', 'y', 'z'}, // 9
        };
        for (int i = 2; i < 10; i++) {
            NumberToChar.put(i, chars[i - 2]);
        }
        Path = new StringBuilder();
        Result = new ArrayList<>();
        BackTracking(digits);
        return Result;
    }
    private void BackTracking(String digits) {
        if (digits.length() == 0) {
            Result.add(Path.toString());
            return;
        }
        char c = digits.charAt(0);
        int index = Integer.parseInt(String.valueOf(c));
        char[] Options = NumberToChar.get(index);
        for (int i = 0; i < Options.length; i++) {
            Path.append(Options[i]);
            BackTracking(digits.substring(1));
            Path.delete(Path.length() - 1, Path.length());
        }
    }
}
