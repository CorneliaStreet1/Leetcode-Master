package Array;

public class minSubArrayLen_209 {

    public int minSubArrayLen(int target, int[] nums) {
        int sum = 0, start = 0, end, result = Integer.MAX_VALUE;
        for (end = 0; end < nums.length; end ++) {
            sum += nums[end];
            while (sum >= target) {
                int Len = end - start + 1;
                result = Math.min(Len, result);
                sum -= nums[start];
                start ++;
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }
    public int my_minSubArrayLen(int target, int[] nums) {
        int Length = 0;
        for (; Length < nums.length; Length++) {
            for (int start = 0; start + Length < nums.length; start ++) {
                int Sum = getSum(nums, start, start + Length);
                if (Sum >= target) {
                    return Length + 1;
                }
            }
        }
        return 0;
    }
    private int getSum(int[] ints, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += ints[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println((new minSubArrayLen_209()).minSubArrayLen(16, new int[]{2, 3, 1, 2, 4, 3}));
    }


}
