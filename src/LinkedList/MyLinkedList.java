package LinkedList;

import Public.ListNode;

public class MyLinkedList {
    private class Node {
        int Val;
        Node Next;
        Node Prev;

        public Node(int val, Node next, Node prev) {
            Val = val;
            Next = next;
            Prev = prev;
        }
    }
    private Node Head;
    private Node Tail;
    private int Size;

    public MyLinkedList() {
        Head = new Node(-1, null, null);
        Tail = new Node(-1, null, null);
        Head.Next = Tail;
        Tail.Prev = Head;
        Size = 0;
    }

    public int get(int index) {
        if (index >= Size || index < 0) {
            return  -1;
        }
        int pos = 0;
        Node p = Head.Next;
        while (pos != index) {
            pos ++;
            p = p.Next;
        }
        return p.Val;
    }

    public void addAtHead(int val) {
        Node OldHead = Head.Next;
        Node NewHead = new Node(val, null, null);
        Head.Next = NewHead;
        NewHead.Prev = Head;
        NewHead.Next = OldHead;
        OldHead.Prev = NewHead;
        Size ++;
    }

    public void addAtTail(int val) {
        Node OldTail = Tail.Prev;
        Node NewTail = new Node(val, null, null);
        Tail.Prev = NewTail;
        NewTail.Next = Tail;
        NewTail.Prev = OldTail;
        OldTail.Next = NewTail;
        Size ++;
    }

    public void addAtIndex(int index, int val) {
        if (index <= Size) {
            if (index == Size) {
                addAtTail(val);
            }else {
                Node p = Head.Next;
                int pos = 0;
                while (pos != index) {
                    pos ++;
                    p = p.Next;
                }
                Node n = new Node(val, null, null);
                p.Prev.Next = n;
                n.Prev = p.Prev;
                n.Next = p;
                p.Prev = n;
                Size ++;
            }
            //Size ++不能放这里，否则假如index == size的时候，size会++两次
        }
        return;
    }

    public void deleteAtIndex(int index) {
        if (index >= Size || index < 0) {
            return ;
        }
        int pos = 0;
        Node p = Head.Next;
        while (pos != index) {
            p = p.Next;
            pos ++;
        }
        p.Prev.Next = p.Next;
        p.Next.Prev = p.Prev;
        Size --;
    }

    public static void main(String[] args) {
        /*
["MyLinkedList","addAtHead9","get","addAtIndex","addAtIndex","deleteAtIndex",
"addAtHead","addAtHead","deleteAtIndex","addAtIndex","addAtHead","deleteAtIndex"]
        * */
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtHead(9);
        myLinkedList.get(1);
        myLinkedList.addAtIndex(1,  1);
        myLinkedList.addAtIndex(1,  7);
        myLinkedList.deleteAtIndex(1);
        myLinkedList.addAtHead(1);
        myLinkedList.addAtHead(4);
        myLinkedList.deleteAtIndex(1);
        myLinkedList.addAtIndex(1, 4);
        myLinkedList.addAtHead(2);
        myLinkedList.deleteAtIndex(5);

    }
}
