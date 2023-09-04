package Hashing;

import java.util.HashMap;

public class ransomLetter_383 {
    public boolean canConstruct(String ransomNote, String magazine) {
        char[] chars = ransomNote.toCharArray();
        char[] chars1 = magazine.toCharArray();
        int[] CharFrequency = new int[26];
        for (char c : chars1) {
            CharFrequency[c - 'a'] ++;
        }
        for (char aChar : chars) {
            CharFrequency[aChar - 'a'] --;
        }
        for (int i : CharFrequency) {
            if (i < 0) {
                return false;
            }
        }
        return true;
    }
}
