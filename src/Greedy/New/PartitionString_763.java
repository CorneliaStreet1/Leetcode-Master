package Greedy.New;

import java.util.*;

public class PartitionString_763 {
    public List<Integer> partitionLabels(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character, int[]> characterHashMap = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            if (!characterHashMap.containsKey(chars[i])) {
                characterHashMap.put(chars[i], new int[] {i, i});
            }else {
                int[] ints = characterHashMap.get(chars[i]);
                ints[1] = i;
            }
        }
        int[][] ints = new int[characterHashMap.size()][2];
        int i = 0;
        for (Character character : characterHashMap.keySet()) {
            int[] ints1 = characterHashMap.get(character);
            ints[i][0] = ints1[0];
            ints[i][1] = ints1[1];
            i ++;
        }
        Arrays.sort(ints, (o1, o2) -> {
            return o1[0] - o2[0];
        });
        ArrayList<Integer> ans = new ArrayList<>();
        int  S = ints[0][0], E = ints[0][1];
        for (int i1 = 1; i1 < ints.length; i1++) {
            if (E < ints[i1][0]) {
                ans.add(E - S + 1);
                S = ints[i1][0];
                E = ints[i1][1];
            }else {
                E = Math.max(E, ints[i1][1]);
            }
        }
        ans.add(E - S + 1);
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new PartitionString_763().partitionLabels("ababcbacadefegdehijhklij"));
    }
}
