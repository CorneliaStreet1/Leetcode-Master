package LinkedList;

import Public.ListNode;

public class reverseList_206 {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public static void main(String[] args) {
        System.out.println((new reverseList_206()).reverseList(ListNode.getListOf(1, 2, 3, 4, 5)));
    }
}
