package Tree;

import Public.MultipleNodeTree.Node;

import java.util.ArrayDeque;
import java.util.Deque;

public class MultiNode_LevelOrder_maxDepth__559 {
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }
        int MaxDepth = 0;
        Deque<Node> NodeQueue = new ArrayDeque<>();
        NodeQueue.addLast(root);
        while (!NodeQueue.isEmpty()) {
            MaxDepth ++;
            int size = NodeQueue.size();
            while (size > 0) {
                Node node = NodeQueue.removeFirst();
                for (Node child : node.children) {
                    NodeQueue.addLast(child);
                }
                size --;
            }
        }
        return MaxDepth;
    }
}
