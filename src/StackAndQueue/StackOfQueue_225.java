package StackAndQueue;

import java.util.LinkedList;
import java.util.Queue;

public class StackOfQueue_225 {
    Queue<Integer> queue;
    public StackOfQueue_225() {
        queue = new LinkedList<>();
    }

    public void push(int x) {
        queue.add(x);
    }

    public int pop() {
        int size = queue.size();
        while (size > 1) {
            queue.add(queue.poll());
            size --;
        }
        return queue.poll();
    }

    public int top() {
        int pop = pop();
        push(pop);
        return pop;
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        StackOfQueue_225 stackOfQueue225 = new StackOfQueue_225();
        stackOfQueue225.push(1);
        stackOfQueue225.push(2);
        System.out.println(stackOfQueue225.top());
        System.out.println(stackOfQueue225.pop());
    }
}
