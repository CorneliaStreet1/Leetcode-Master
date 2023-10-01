package DynamicPlaning;

public class UniqueBSTCount_96 {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        if (n <= 2) {
            return n;
        }
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <=n ; i++) { // i是dp[i]的i，也即我们要求以1到i这i个数构成的BST数目。

            // 内层循环穷举根节点依次为1,2,3,直到i的情况,从而求出dp[i]
            for (int root = 1; root <= i ; root++) {
                // 左子树的节点: 1到root - 1。右子树的节点: root + 1到i
                int LeftNum = root - 1, RightNum = i - root;
                dp[i] += dp[LeftNum] * dp[RightNum];
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        (new UniqueBSTCount_96()).numTrees(4);
    }
}
