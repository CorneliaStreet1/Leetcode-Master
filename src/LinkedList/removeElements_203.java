package LinkedList;

import Public.ListNode;

public class removeElements_203 {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        ListNode VirtualHead = new ListNode(-1, head);
        ListNode Prev = VirtualHead, Current = head;
        while (Current != null) {
            if (Current.val == val) {
                Prev.next = Current.next;
            }else {
                Prev = Prev.next;
            }
            Current = Current.next;
        }
        return VirtualHead.next;
    }

    public static void main(String[] args) {
        ListNode listOf = ListNode.getListOf(1, 1, 4, 5, 1, 4);
        System.out.println((new removeElements_203()).removeElements(listOf, 1));
    }
}
