package BackTracking.New;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordBreak_139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length() ; i++) {
            for (String s1 : wordDict) {
                // i - (j + 1)  + 1= s1.length()
                int j = i - s1.length();
                if (j >=0 && dp[j] && s.substring(j, i).equals(s1)) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        String[] strings1 = {"cats", "dog", "sand", "and", "cat"};
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(strings1));
        (new WordBreak_139()).wordBreak("catsandog", strings);
    }
}
