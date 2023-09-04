package Tree;


import java.util.ArrayDeque;
import java.util.Deque;

import Public.BinaryTree.*;


public class ConnectNext_116 {
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        Deque<Node> NodeQueue = new ArrayDeque<>();
        NodeQueue.addLast(root);
        while (!NodeQueue.isEmpty()) {
            int size = NodeQueue.size();
            if (size == 1) {
                Node node = NodeQueue.removeFirst();
                node.next = null;
                if (node.left != null) {
                    NodeQueue.addLast(node.left);
                    NodeQueue.addLast(node.right);
                }
            }else {
                Node Prev = NodeQueue.removeFirst();
                size --;
                while (size > 0) {
                    if (Prev.left != null && Prev.right != null) {
                        NodeQueue.add(Prev.left);
                        NodeQueue.add(Prev.right);
                    }
                    Node Next = NodeQueue.removeFirst();
                    Prev.next = Next;
                    Prev = Next;
                    size --;
                }
                if (Prev.left != null && Prev.right != null) {
                    NodeQueue.add(Prev.left);
                    NodeQueue.add(Prev.right);
                }
            }
        }
        return root;
    }

    public static void main(String[] args) {
        Node node = new Node(1, new Node(2, new Node(4), new Node(5), null), new Node(3, new Node(6), new Node(7), null), null);
        (new ConnectNext_116()).connect(node);
    }
}
