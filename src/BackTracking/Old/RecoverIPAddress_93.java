package BackTracking.Old;

import java.util.ArrayList;
import java.util.List;

public class RecoverIPAddress_93 {

    List<String> Result;
    StringBuilder IP;
    int SegmentNum;

    public List<String> restoreIpAddresses(String s) {
        Result = new ArrayList<>();
        IP = new StringBuilder();
        SegmentNum = 0;
        BackTracking(s, 0);
        return Result;
    }
    private void BackTracking(String s, int StartIndex) {
        if (SegmentNum == 4) {
            IP.delete(IP.length() - 1, IP.length());
            Result.add(IP.toString());
            return;
        }
        for (int i = StartIndex; i < s.length(); i++) {
            if (isValid(s, StartIndex, i + 1)) {
                String substring = s.substring(StartIndex, i + 1);
                substring += ".";
                SegmentNum++;
                IP.append(substring);
                BackTracking(s, i + 1);
                IP.delete(IP.length() - substring.length(), IP.length());
                SegmentNum--;
            }
        }
    }
    private boolean isValid(String s, int Start, int End) {
        if (End < Start) {
            return false;
        }
        if (Start != End && s.charAt(Start) == '0') {
            return false;
        }
        if (End - Start > 2) {
            return false;
        }
        String substring = s.substring(Start, End);
        int i = Integer.parseInt(substring);
        return i <= 255;
    }

    public static void main(String[] args) {
        System.out.println((new RecoverIPAddress_93()).restoreIpAddresses("0000"));
    }
}
