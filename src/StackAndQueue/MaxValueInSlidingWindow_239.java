package StackAndQueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MaxValueInSlidingWindow_239 {
    private class  MonotonicQueue {
        Deque<Integer> integerDeque;

        public MonotonicQueue() {
            integerDeque = new ArrayDeque<>();
        }
        public void Pop(int val) {
            if (!integerDeque.isEmpty() && val == integerDeque.peek()) {
                integerDeque.poll();
            }
        }
        public void Push(int val) {
            while (!integerDeque.isEmpty() && val > integerDeque.getLast()) {
                integerDeque.removeLast();
            }
            integerDeque.addLast(val);
        }
        public int Peek() {
            return integerDeque.getFirst();
        }
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 1) {
            return nums;
        }
        int len = nums.length - k + 1;
        //存放结果元素的数组
        int[] res = new int[len];
        int num = 0;
        //自定义队列
        MonotonicQueue myQueue = new MonotonicQueue();
        //先将前k的元素放入队列
        for (int i = 0; i < k; i++) {
            myQueue.Push(nums[i]);
        }
        res[num++] = myQueue.Peek();
        for (int i = k; i < nums.length; i++) {
            //滑动窗口移除最前面的元素，移除是判断该元素是否放入队列
            myQueue.Pop(nums[i - k]);
            //滑动窗口加入最后面的元素
            myQueue.Push(nums[i]);
            //记录对应的最大值
            res[num++] = myQueue.Peek();
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString((new MaxValueInSlidingWindow_239()).maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
    }
}
