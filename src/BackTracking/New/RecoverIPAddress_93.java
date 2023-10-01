package BackTracking.New;

import java.util.ArrayList;
import java.util.List;

public class RecoverIPAddress_93 {

    StringBuilder stringBuilder;
    List<String> Result;
    int Count;
    public List<String> restoreIpAddresses(String s) {
        Result = new ArrayList<>();
        stringBuilder = new StringBuilder();
        Count = 0;
        BackTracking(s, 0);
        return Result;
    }
    private void BackTracking(String s, int StartIndex) {
        if (Count == 4) {
            String s1 = stringBuilder.toString();
            if (s1.length() == s.length() + 4) {
                Result.add(s1.substring(0, s1.length() - 1));
            }
            return;
        }
        for (int i = StartIndex; i < s.length(); i ++) {
            String substring = s.substring(StartIndex, i + 1);
            if (isValidIP(substring)) {
                stringBuilder.append(substring);
                stringBuilder.append(".");
                Count ++;
                BackTracking(s, i + 1);
                Count --;
                // substring.Length() + 1 = stringBuilder.length() - 1 - x + 1
                stringBuilder.delete(stringBuilder.length() - substring.length() - 1, stringBuilder.length());
            }
        }
    }
    private boolean isValidIP(String s) {
        if (s.length() < 1) {
            return false;
        }
        if (s.length() == 1 && s.charAt(0) == '0') {
            return true;
        }
        if (s.length() > 1 && s.charAt(0) == '0') {
            return false;
        }
        if (s.length() > 3) {
            return false;
        }
        int i = Integer.parseInt(s);
        return i <= 255;
    }

    public static void main(String[] args) {
        System.out.println((new RecoverIPAddress_93()).restoreIpAddresses("25525511135"));
    }
}
