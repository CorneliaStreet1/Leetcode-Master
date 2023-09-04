package Hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class isAnagram_242 {

    public boolean isAnagram(String s, String t) {
        int[] frequency = new int[26];
        int[] frequency1 = new int[26];
        char[] chars = s.toCharArray();
        char[] chars1 = t.toCharArray();
        for (char aChar : chars) {
            frequency[aChar - 'a'] ++;
        }
        for (char c : chars1) {
            frequency1[c - 'a'] ++;
        }
        return Arrays.equals(frequency, frequency1);
    }
    public boolean isAnagram1(String s, String t) {
        char[] chars = s.toCharArray();
        char[] chars1 = t.toCharArray();
        HashMap<Character, Integer> map1 = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();
        for (char aChar : chars) {
            if (map1.containsKey(aChar)) {
                map1.put(aChar, map1.get(aChar) + 1);
            }else {
                map1.put(aChar, 1);
            }
        }
        for (char c : chars1) {
            if (map2.containsKey(c)) {
                map2.put(c, map2.get(c) + 1);
            }else {
                map2.put(c, 1);
            }
        }
        for (char aChar : chars) {
            if (map2.get(aChar) == null || map1.get(aChar).intValue() != map2.get(aChar)) {
                return false;
            }
        }
        for (char aChar : chars1) {
            if (map1.get(aChar) == null || map1.get(aChar).intValue() != map2.get(aChar)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        (new isAnagram_242()).isAnagram("a", "ab");
    }
}
