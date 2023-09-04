package String;

public class repeatedSubstringPattern_459 {
    public boolean repeatedSubstringPattern(String s) {
        char[] chars = s.toCharArray();
        for (int i = 1; i * 2 <= s.length() ; i++) {
            if (s.length() % i == 0) {
                boolean match = true;
                for (int j = i; j < s.length();  j++) {
                    if (chars[j] != chars[j - i]) {
                        match = false;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }
}
