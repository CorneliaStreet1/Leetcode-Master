package String;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class reverseWords_151 {
    public String reverseWords(String s) {
        List<String> stringList = RemoveSpace(s);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = stringList.size() - 1; i >= 0; i --) {
            stringBuilder.append(stringList.get(i));
            if (i != 0) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
    private  List<String> RemoveSpace(String s) {
        char[] chars = s.toCharArray();
        ArrayList<String> strings = new ArrayList<>();
        int Start = 0, End = 0;
        while (End < chars.length) {
            if (chars[Start] != ' ') {
                while (End < chars.length  && chars[End] != ' ') {
                    End ++;
                }
                strings.add(String.valueOf(chars, Start, End - Start));
                Start = End;
            }else {
                Start ++;
                End ++;
            }
        }
        return strings;
    }

    public static void main(String[] args) {
        System.out.println((new reverseWords_151()).reverseWords("   abc   d efgh aaa"));
    }
}
