package LinkedList;

import Public.ListNode;

import java.util.HashSet;
import java.util.Set;

public class detectCycleII_142 {
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, A = head;
        ListNode slow = head, B = null;
        /*
        * 因为循环里要用到fast.next.next，所以要求fast.next != null
        * 因为fast.next要用到fast，所以要求fast != null
        * 因为循环里要用到 slow.next，所以要求slow != null
        * 都是为了不触发空指针异常
        * */
        while (fast != null && fast.next != null && slow != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                B = slow;
                while (A !=  B) {
                    A = A.next;
                    B = B.next;
                }
                return A;
            }
        }
        return null;
    }

}
