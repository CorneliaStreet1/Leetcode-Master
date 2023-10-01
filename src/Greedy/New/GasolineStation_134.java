package Greedy.New;

public class GasolineStation_134 {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int Gas = 0;
        for (int i = 0; i < gas.length; i++) {
            int j = (i + 1)  % gas.length;
            Gas = gas[i] - cost[i];
            while (Gas > 0 && j != i) {
                Gas += gas[j];
                Gas -= cost[j];
                j = (j + 1) % gas.length;
            }
            if (j == i && Gas >= 0) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        (new GasolineStation_134()).canCompleteCircuit(new int[] {4,5,3,1,4}, new int[]{5,4,3,4,2});
    }
}
