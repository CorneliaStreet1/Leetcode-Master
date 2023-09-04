package LinkedList;

import Public.ListNode;

public class getIntersectionNode_0207 {
    /*
      面试题 02.07. 链表相交
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null && headB != null) {
            return null;
        }
        if (headA != null && headB == null) {
            return null;
        }
        ListNode A = headA, B = headB;
        while (A != B) {
            if (A == null) {
                A = headB;
            }else {
                A = A.next;
            }
            if (B == null) {
                B = headA;
            }else {
                B = B.next;
            }
        }
        return A;
    }

    public static void main(String[] args) {
        ListNode pub = new ListNode(8, new ListNode(4, new ListNode(5)));
        ListNode ha = new ListNode(4, new ListNode(1, pub)),
                hb = new ListNode(5, new ListNode(0, new ListNode(1, pub)));
        //(new getIntersectionNode_0207()).getIntersectionNode(ha, hb);
        (new getIntersectionNode_0207()).getIntersectionNode(ha, new ListNode(1, new ListNode(2, new ListNode(3))));
    }
}
