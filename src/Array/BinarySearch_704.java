package Array;

public class BinarySearch_704 {
    public int search(int[] nums, int target) {
        int Left_Index = 0, Right_Index = nums.length - 1;
        while (Left_Index <= Right_Index) {
            int Mid_Index = (Left_Index + Right_Index) / 2; // 防止溢出 可使用 left + ((right - left) / 2)
            if (nums[Mid_Index] > target) {
                Right_Index = Mid_Index - 1;
            }
            else if (nums[Mid_Index] < target) {
                Left_Index = Mid_Index + 1;
            }else {
                return Mid_Index;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println((new BinarySearch_704()).search(new int[]{-1,0,3,5,9,12}, 2));
    }
}
