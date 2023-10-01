package DynamicPlaning.ZeroOneBackpack;

public class ZeroAndOnes_474 {
    public int findMaxForm(String[] strs, int m, int n) {
        int[] ZeroWeight = new int[strs.length];
        int[] OneWeight = new int[strs.length];
        int i = 0;
        for (String str : strs) {
            char[] chars = str.toCharArray();
            for (char aChar : chars) {
                if (aChar == '0') {
                    ZeroWeight[i] ++;
                }else {
                    OneWeight[i] ++;
                }
            }
            i ++;
        }
        int i1 = BackPacking_Squared(m, n, ZeroWeight, OneWeight, 1);
        return i1;
    }

    private int BackPacking_Squared(int ZeroCapa, int OneCapa, int[] ZeroWeight, int[] OneWeight, int Value) {
        int[][][] dp = new int[ZeroWeight.length][ZeroCapa + 1][OneCapa + 1];
        for (int i = 0; i < dp[0].length; i++) {
            for (int j = 0; j < ZeroCapa + 1; j++) {
                for (int k = 0; k < OneCapa + 1; k++) {
                    if (j >= ZeroWeight[0] && k >= OneWeight[0]) {
                        dp[0][j][k] = Value;
                    }else {
                        dp[0][j][k] = 0;
                    }
                }
            }
        }
        for (int i = 1; i < ZeroWeight.length; i++) {
            for (int j = 0; j < ZeroCapa + 1; j++) {
                for (int k = 0; k < OneCapa + 1; k++) {
                    if (j >= ZeroWeight[i] && k >= OneWeight[i]) {
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - ZeroWeight[i]][k - OneWeight[i]] + Value);
                    }else {
                        dp[i][j][k] = dp[i - 1][j][k];
                    }
                }
            }
        }
        return dp[ZeroWeight.length - 1][ZeroCapa][OneCapa];
    }

    public static void main(String[] args) {
        (new ZeroAndOnes_474()).findMaxForm(new String[]{"10","0001","111001","1","0"}, 5, 3);
    }
}
