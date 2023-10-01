package Greedy.New;

public class MaxSubArray_53 {
    public int maxSubArray(int[] nums) {
        int sum = nums[0], max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (sum >= 0) {
                sum += nums[i];
            }else {
                sum = nums[i];
            }
            max = Math.max(max, sum);
        }
        return max;
    }

    public static void main(String[] args) {
        (new MaxSubArray_53()).maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4});
    }
}
