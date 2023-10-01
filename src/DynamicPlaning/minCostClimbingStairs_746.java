package DynamicPlaning;

public class minCostClimbingStairs_746 {
    public int minCostClimbingStairs(int[] cost) {
        int[] overhead = new int[cost.length + 1];
        overhead[0] = 0;
        overhead[1] = 0;
        for (int i = 2; i < overhead.length; i++) {
            overhead[i] = Math.min(overhead[i - 1] + cost[i - 1], overhead[i - 2] + cost[i - 2]);
        }
        return overhead[cost.length];
    }

    public static void main(String[] args) {
        System.out.println((new minCostClimbingStairs_746()).minCostClimbingStairs(new int[]{10, 15, 20}));
    }
}
