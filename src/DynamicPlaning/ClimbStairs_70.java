package DynamicPlaning;

public class ClimbStairs_70 {
    public int climbStairs(int n) {
        if (n <= 1 ) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int[] Paths = new int[n];
        Paths[0] = 1;
        Paths[1] = 2;
        for (int i = 2; i < n; i++) {
            Paths[i] = Paths[i - 1] + Paths[i - 2];
        }
        return Paths[n - 1];
    }
}
