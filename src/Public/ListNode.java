package Public;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
    public static ListNode getListOf(int... vals) {
        ListNode ret = null, CurrentNode = null;
        for (int i : vals) {
            if (ret == null) {
                ret = new ListNode(i);
                CurrentNode = ret;
            }
            else {
                ListNode node = new ListNode(i);
                CurrentNode.next = node;
                node.next = null;
                CurrentNode = node;
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        ListNode p = this;
        StringBuilder stringBuilder = new StringBuilder();
        while (p != null) {
            stringBuilder.append(p.val).append(" ");
            p = p.next;
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(ListNode.getListOf(1,2,3,4,5));
    }
}
