package DynamicPlaning;

public class UniquePath_62 {
    public int uniquePaths(int m, int n) {
        int[][] DP = new int[m][n]; // DP[i][j]: 从起点(0,0)到达(i,j)存在的不同路径数
        DP[0][0] = 1;
        for (int i = 1; i < DP[0].length; i++) {
            DP[0][i] = DP[0][i - 1];
        }
        for (int i = 1; i < DP.length; i++) {
            DP[i][0] = DP[i - 1][0];
        }
        for (int i = 1; i < DP.length; i++) {
            for (int j = 1; j < DP[0].length; j++) {
                DP[i][j] = DP[i - 1][j] + DP[i][j - 1];
            }
        }
        return DP[m - 1][n - 1];
    }

}
