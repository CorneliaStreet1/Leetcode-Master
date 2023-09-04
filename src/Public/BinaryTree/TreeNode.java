package Public.BinaryTree;

public class TreeNode {


    public int val;

    public TreeNode left;

    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {

        this.val = val;

        this.left = left;

        this.right = right;

    }
    public static TreeNode getTreeOf(int... nodeValues) {
        TreeNode root = new TreeNode();
        TreeNode parent,left,right;
        for (int i: nodeValues
        ) {

        }
        return root;
    }
}
