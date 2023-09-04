package StackAndQueue;

import java.util.LinkedList;

public class QueueOfStack_232 {
    // 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作
    //  只能 使用标准的栈操作 —— 也就是只有 push / pop , size, 和 is empty 操作是合法的。

    LinkedList<Integer> InStack;
    LinkedList<Integer> OutStack;
    public QueueOfStack_232() {
        InStack = new LinkedList<>();
        OutStack = new LinkedList<>();
    }

    public void push(int x) {
        InStack.push(x);
    }

    public int pop() {
        if (OutStack.isEmpty()) {
            while (!InStack.isEmpty()) {
                OutStack.push(InStack.pop());
            }
        }
        return OutStack.pop();
    }

    public int peek() {
        if (OutStack.isEmpty()) {
            while (!InStack.isEmpty()) {
                OutStack.push(InStack.pop());
            }
        }
        assert OutStack.peek() != null;
        return OutStack.peek();
    }

    public boolean empty() {
        return InStack.isEmpty() && OutStack.isEmpty();
    }
}
