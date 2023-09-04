package LinkedList;

import Public.ListNode;

public class removeNthFromEnd_19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode VirtualHead = new ListNode();
        VirtualHead.next = head;
        ListNode Fast = VirtualHead, Slow = VirtualHead;
        head = null;
        for (int i = 0; i < n; i++) {
            Fast = Fast.next;
        }
        while (Fast.next != null) {
            Fast = Fast.next;
            Slow = Slow.next;
        }
        Slow.next = Slow.next.next;
        return VirtualHead.next;
    }

    public static void main(String[] args) {
        System.out.println((new removeNthFromEnd_19()).removeNthFromEnd(ListNode.getListOf(1,2, 3), 3));
    }
}
