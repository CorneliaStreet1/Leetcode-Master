package Tree;

import Public.MultipleNodeTree.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LevelOrderTravelsal_N_429 {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<Node> Queue = new ArrayDeque<>();
        Queue.addLast(root);
        while (!Queue.isEmpty()) {
            int size = Queue.size();
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (size > 0) {
                Node node = Queue.removeFirst();
                for (Node child : node.children) {
                    Queue.addLast(child);
                }
                arrayList.add(node.val);
                size --;
            }
            result.add(arrayList);
        }
        return result;
    }
}
