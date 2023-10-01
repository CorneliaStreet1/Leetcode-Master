package Tree;

import Public.BinaryTree.TreeNode;

import java.util.HashMap;

public class ConstructBSTBy_PreAndInOrder_105 {
;
    HashMap<Integer, Integer> ValToIndex;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        ValToIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            ValToIndex.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }
    private TreeNode buildTreeHelper(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }
        else {
            int rootVal = preorder[preStart];
            int rootIndex = ValToIndex.get(rootVal);
            TreeNode root = new TreeNode(rootVal);
            int LeftLen = (rootIndex - 1) - inStart + 1;
            int RightLen = inEnd - (rootIndex + 1) + 1;
            /*
            中序遍历:左 中 右
            前序遍历 中 左 右
            左子树的中序: inStart ==> rootIndex - 1.长度是 rootIndex - inStart
            右子树的中序: rootIndex + 1 ==>inEnd
            左子树的前序:preStart + 1 ==> LeftLen + preStart = rootIndex - inStart + preStart; x - (preStart + 1) + 1 = LeftLen
            右子树的前序: LeftLen + preStart + 1 ==> LeftLen + preStart + RightLen = ;y - (LeftLen + preStart + 1) + 1 = RightLen
            */
            root.left = buildTreeHelper(preorder, inorder, preStart + 1, LeftLen + preStart, inStart, rootIndex - 1);
            root.right = buildTreeHelper(preorder, inorder, LeftLen + preStart + 1, LeftLen + preStart + RightLen, rootIndex + 1, inEnd);
            return root;
        }
    }
}
