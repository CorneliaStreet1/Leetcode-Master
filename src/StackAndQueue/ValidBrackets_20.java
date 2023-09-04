package StackAndQueue;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Stack;

public class ValidBrackets_20 {
    /*
    * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
    * 有效字符串需满足：
    * 左括号必须用相同类型的右括号闭合。
    * 左括号必须以正确的顺序闭合。
    * 每个右括号都有一个对应的相同类型的左括号。
    * */
    public boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }
        char[] chars = s.toCharArray();
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '[') {
                stack.addLast(']');
            }
            else if (chars[i] == '(') {
                stack.addLast(')');
            }
            else if (chars[i] == '{') {
                stack.addLast('}');
            }else {
                // 假如是一个正确的串,Stack里必然有一个与 chars[i] 相同的 ']'或 ')' 或'}所以如果Stack为空，直接return false
                if (stack.isEmpty()) {
                    return false;
                }
                if (chars[i] == ']') {
                    // 因为removeLast在链表为空的情况下会抛出异常，所以假如Stack是空，那么肯定是 return false
                    Character character = stack.removeLast();
                    if (character != ']') {
                        return false;
                    }
                }
                else if (chars[i] == ')') {
                    Character character = stack.removeLast();
                    if (character != ')') {
                        return false;
                    }
                }
                else if (chars[i] == '}') {
                    Character character = stack.removeLast();
                    if (character != '}') {
                        return false;
                    }
                }
            }
        }
        return stack.isEmpty();
    }
}
