package Tree;

import Public.BinaryTree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class getMinimumDifference_530 {
    int MinVal;
    int PrevVal;
    public int getMinimumDifference(TreeNode root) {
        MinVal = Integer.MAX_VALUE;
        PrevVal = -1;
        Helper(root);
        return MinVal;
    }
    private void  Helper(TreeNode root) {
        if (root == null) {
            return;
        }
        Helper(root.left);
        if (PrevVal == -1) {
            PrevVal = root.val;
        }
        else {
            MinVal = Math.min(MinVal, root.val - PrevVal);
            PrevVal = root.val;
        }
        Helper(root.right);
    }

}
