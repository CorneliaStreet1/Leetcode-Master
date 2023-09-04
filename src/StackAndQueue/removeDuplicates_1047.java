package StackAndQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class removeDuplicates_1047 {
    public String removeDuplicates(String s) {
        char[] chars = s.toCharArray();
        Deque<Character> characters = new ArrayDeque<>();
        for (char aChar : chars) {
            if (!characters.isEmpty() && aChar == characters.peek()) {
                characters.pop();
            }else {
                characters.push(aChar);
            }
        }
        StringBuilder s1 = new StringBuilder();
        while (!characters.isEmpty()) {
            s1.append(characters.pop());
        }
        return s1.reverse().toString();
    }

    // 直接使用StringBuilder做栈
    public String removeDuplicates_Fast(String s) {
        char[] chars = s.toCharArray();
        StringBuilder characters = new StringBuilder();
        for (char aChar : chars) {
            if (characters.length() != 0 && aChar == characters.charAt(characters.length() - 1)) {
                characters.delete(characters.length() - 1, characters.length());
            }else {
                characters.append(aChar);
            }
        }
        // 这里不需要翻转,因为没有出栈,之前之所以要反转是因为字符出栈的时候顺序被反转了,所以要反转回来。而这里没出栈,也就不需要反转
        return characters.toString();
    }

    public static void main(String[] args) {
        System.out.println((new removeDuplicates_1047()).removeDuplicates_Fast("abbaca"));
    }
}
