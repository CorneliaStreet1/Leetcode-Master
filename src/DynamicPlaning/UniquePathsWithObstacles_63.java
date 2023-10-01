package DynamicPlaning;

public class UniquePathsWithObstacles_63 {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length]; // dp[i][j]:从起点到(i,j)的路径数
        dp[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;
        for (int i = 1; i < dp[0].length; i++) {
            /*
            如果(0,i)位置本身就有障碍的话，那dp[0][i] = 0。
            否则才等于obstacleGrid[0][i - 1] == 1 ? 0 : dp[0][i - 1]，也即如果前一个格子有障碍也是0，否则和前一个格子的dp相等
            填充dp数组的其他部分也同理。
             */
            dp[0][i] = obstacleGrid[0][i] == 1 ? 0 : (obstacleGrid[0][i - 1] == 1 ? 0 : dp[0][i - 1]);
        }
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = obstacleGrid[i][0] == 1 ? 0 : (obstacleGrid[i - 1][0] == 1 ? 0 : dp[i - 1][0]);
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                }
                else {
                    dp[i][j] = (obstacleGrid[i - 1][j] == 1 ? 0 : dp[i - 1][j]) + (obstacleGrid[i][j - 1] == 1 ? 0 : dp[i][j - 1]);
                }
            }
        }
        return dp[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
    }

    public static void main(String[] args) {
        (new UniquePathsWithObstacles_63()).uniquePathsWithObstacles(new int[][]{
                {0},
                {0},
                {0},
                {0},
        });
    }
}
