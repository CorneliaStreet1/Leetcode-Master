package Tree;

import Public.BinaryTree.TreeNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NearestSharedAncestor_236 {
    TreeNode ans;
    boolean find;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return null;
    }

    private TreeNode getlowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        HashMap<TreeNode, TreeNode> SonToParent = new HashMap<>();
        getMapping(root, SonToParent);
        Set<TreeNode> QPath = new HashSet<>();
        TreeNode Ancestor = p;
        while (Ancestor != root) {
            QPath.add(Ancestor);
            Ancestor = SonToParent.get(Ancestor);
        }

        TreeNode qAncestor = q;
        while (qAncestor != root) {
            if (QPath.contains(qAncestor)) {
                return qAncestor;
            }
            qAncestor = SonToParent.get(qAncestor);
        }
        return root;
    }
    private void getMapping(TreeNode root, HashMap<TreeNode, TreeNode> map) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            map.put(root.left, root);
        }
        if (root.right != null) {
            map.put(root.right, root);
        }
        getMapping(root.left, map);
        getMapping(root.right, map);
    }
    private boolean containsChild(TreeNode root, TreeNode p, TreeNode q) {
        return false;
    }
}
