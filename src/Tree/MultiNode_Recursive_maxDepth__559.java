package Tree;

import Public.MultipleNodeTree.Node;
public class MultiNode_Recursive_maxDepth__559 {
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }
        int MaxChildDepth = 0;
        for (Node child : root.children) {
            MaxChildDepth = Math.max(MaxChildDepth, maxDepth(child));
        }
        return MaxChildDepth + 1;
    }
}
