package Greedy.Old;

public class MaxSubarraySum_53 {

    public int maxSubArray(int[] nums) {
        int Sum = 0, Max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (Sum < 0) {
                Sum = nums[i];
                Max = Math.max(Sum, Max);
            }
            else {
                Sum += nums[i];
                Max = Math.max(Max, Sum);
            }
        }
        return Max;
    }
/*    public int maxSubArray(int[] nums) {
        int Max = Integer.MIN_VALUE;
        for (int Len = 1; Len <= nums.length; Len++) {
            for (int i = 0; i + Len <= nums.length; i++) {
                int i1 = GetSum(i, i + Len - 1, nums);
                if (i1 > Max) {
                    Max = i1;
                }
            }
        }
        return Max;
    }
    private int GetSum(int Start, int End, int[] nums) {
        int Sum = 0;
        for (int i = Start; i <= End ; i++) {
            Sum += nums[i];
        }
        return Sum;
    }*/

    public static void main(String[] args) {
        System.out.println((new MaxSubarraySum_53()).maxSubArray(new int[]{-2, 1}));
    }
}
