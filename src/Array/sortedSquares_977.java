package Array;

import java.util.Arrays;

public class sortedSquares_977 {
    public int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];
        int Left = 0, Right = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if ( (nums[Left] * nums[Left]) > (nums[Right] * nums[Right]) ) {
                result[i] = nums[Left] * nums[Left];
                Left ++;
            }else {
                result[i] = nums[Right] * nums[Right];
                Right --;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString((new sortedSquares_977()).sortedSquares(new int[]{-2, -1, 0, 1, 2})));
    }
}
