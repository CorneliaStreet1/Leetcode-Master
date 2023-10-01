package Tree;

import Public.BinaryTree.TreeNode;

public class ConstructBST_By_InAndPostOrder_106 {
    // 根据二叉树的前序和后序遍历构建二叉树
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        //return buildChildTree(inorder, postorder);
        return buildChildTree_optimized(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }
    private TreeNode buildChildTree_optimized(int[] inorder, int inStart, int inEnd, int[] postorder, int PoStart, int PoEnd) {
        if (PoStart > PoEnd) {
            return null;
        }
        else {
            int rootVal = postorder[PoEnd];
            TreeNode rootNode = new TreeNode(rootVal);
            int i = inStart; // i是rootVal在前序里的索引
            for (; i <= inEnd; i++) {
                if (inorder[i] == rootVal) {
                    break;
                }
            }
            // 左子树的中序inStart ==> i -1.右子树是 i + 1 ==> inEnd
            int L = PoStart, R = PoStart;
            for (; L <= PoEnd ; L ++) {
                boolean find = false;
                for (int i1 = inStart; i1 < i; i1 ++) {
                    if (inorder[i1] == postorder[L]) {
                        find = true;
                        break;
                    }
                }
                if (find) {
                    break;
                }
            }
            for (; R <=PoEnd; R ++) {
                boolean find = false;
                for (int i1 = i + 1; i1 <= inEnd; i1 ++) {
                    if (inorder[i1] == postorder[R]) {
                        find = true;
                        break;
                    }
                }
                if (find) {
                    break;
                }
            }
            // 左子树的后序:L ==> L + i - inStart - 1.右子树是 R ==> R + inEnd - i - 1
            rootNode.left = buildChildTree_optimized(inorder, inStart, i - 1, postorder, L, L + i - inStart - 1);
            rootNode.right = buildChildTree_optimized(inorder, i + 1, inEnd, postorder, R, R + inEnd - i - 1);
            return rootNode;
        }
    }
    private TreeNode buildChildTree(int[] inorder, int[] postorder) {
        if (postorder.length == 0) {
            return null;
        }
        else {
            int rootVal = postorder[postorder.length - 1];
            TreeNode rootNode = new TreeNode(rootVal);
            int i = 0;
            for (; i < inorder.length; i++) {
                if (inorder[i] == rootVal) {
                    break;
                }
            }
            int[] LeftInorder = new int[i];
            int[] RightInorder = new int[inorder.length - LeftInorder.length - 1];
            System.arraycopy(inorder, 0,LeftInorder, 0,LeftInorder.length);
            System.arraycopy(inorder, i + 1,RightInorder, 0,RightInorder.length);
            int L = 0, R = 0;
            for (; L < postorder.length; L ++) {
                boolean find = false;
                for (int i1 : LeftInorder) {
                    if (i1 == postorder[L]) {
                        find = true;
                        break;
                    }
                }
                if (find) {
                    break;
                }
            }
            for (; R < postorder.length; R ++) {
                boolean find = false;
                for (int i1 : RightInorder) {
                    if (i1 == postorder[R]) {
                        find = true;
                        break;
                    }
                }
                if (find) {
                    break;
                }
            }
            int[] LeftPostOrder = new int[LeftInorder.length];
            int[] RightPostOrder = new int[RightInorder.length];
            System.arraycopy(postorder, L, LeftPostOrder, 0, LeftPostOrder.length);
            System.arraycopy(postorder, R, RightPostOrder, 0, RightPostOrder.length);
            rootNode.left = buildChildTree(LeftInorder, LeftPostOrder);
            rootNode.right = buildChildTree(RightInorder, RightPostOrder);
            return rootNode;
        }
    }

    public static void main(String[] args) {
        int[] in = new int[]{9,3,15,20,7};
        int[] post = new int[]{9,15,7,20,3 };
        (new ConstructBST_By_InAndPostOrder_106()).buildTree(in, post);
    }
}
