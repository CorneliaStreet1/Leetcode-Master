package LinkedList;

import Public.ListNode;

public class SwapNodes_24 {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next ==  null) {
            return head;
        }
        ListNode p = head, n = p.next, prev = null;
        head = n;
        p.next = n.next;
        n.next = p;
        prev = p;
        p = p.next;
        if (p == null) {
            return head;
        }
        n = p.next;
        while (n != null) {
            p.next = n.next;
            n.next = p;
            prev.next = n;
            prev = p;
            p = p.next;
            if (p == null) {
                break;
            }
            n = p.next;
        }
        return head;
    }

    public static void main(String[] args) {
        System.out.println((new SwapNodes_24()).swapPairs(ListNode.getListOf(1, 2, 3)));
    }
}
