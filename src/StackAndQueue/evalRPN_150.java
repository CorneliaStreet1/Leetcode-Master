package StackAndQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class evalRPN_150 {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
                if (token.equals("+")) {
                    int num1 =  (stack.pop());
                    int num2 =  (stack.pop());
                    stack.push(num1 + num2);
                }
                else if (token.equals("-")) {
                    int num1 =  (stack.pop());
                    int num2 =  (stack.pop());
                    stack.push(num2 - num1);
                }
                else if (token.equals("*")) {
                    int num1 =  (stack.pop());
                    int num2 =  (stack.pop());
                    stack.push( num2 * num1);
                }
               else if (token.equals("/")) {
                    int num1 =  (stack.pop());
                    int num2 =  (stack.pop());
                    stack.push( num2 / num1 );
                }
                else {
                stack.push(Integer.parseInt(token));
                }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        System.out.println((new evalRPN_150()).evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}));
    }
}
