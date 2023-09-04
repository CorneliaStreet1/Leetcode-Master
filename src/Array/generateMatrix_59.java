package Array;

import java.util.Arrays;

public class generateMatrix_59 {
    public int[][] generateMatrix(int n) {
        int[][] ret = new int[n][n];
        int startX = 0, startY = 0, StartValue = 1;
        if (n % 2 == 0) {
            int Len = n -1;
            for (int i = 0; i < (n / 2 ); i++) {
                for (int j = 0; j < Len; j++) {
                    ret[startX][startY + j] = (StartValue + j);
                }
                StartValue += Len;
                for (int j = 0; j < Len; j++) {
                    ret[startX  + j][startY + Len] = (StartValue + j);
                }
                StartValue += Len;
                for (int j = 0; j < Len; j++) {
                    ret[startX + Len][startY + Len - j] = (StartValue + j);
                }
                StartValue += Len;
                for (int j = 0; j < Len; j++) {
                    ret[startX + Len - j][startY] = (StartValue + j) ;
                }
                StartValue += Len;
                startX ++;
                startY ++;
                Len -= 2;
            }
        }else {
            int Len = n -1;
            for (int i = 0; i < (n / 2 ); i++) {
                for (int j = 0; j < Len; j++) {
                    ret[startX][startY + j] = (StartValue + j);
                }
                StartValue += Len;
                for (int j = 0; j < Len; j++) {
                    ret[startX  + j][startY + Len] = (StartValue + j);
                }
                StartValue += Len;
                for (int j = 0; j < Len; j++) {
                    ret[startX + Len][startY + Len - j] = (StartValue + j);
                }
                StartValue += Len;
                for (int j = 0; j < Len; j++) {
                    ret[startX + Len - j][startY] = (StartValue + j);
                }
                StartValue += Len;
                startX ++;
                startY ++;
                Len -= 2;
            }
            ret[(n - 1) / 2][(n - 1) / 2] = n * n;
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString((new generateMatrix_59()).generateMatrix(6)));
    }
}
