package Array;

public class removeElement_27 {
    public int removeElement(int[] nums, int val) {
        int  p = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[p] = nums[i];
                p ++;
            }
        }
        return p;
    }
    private void MoveLeft(int[] nums, int empty, int sz) {
        for (int i = empty + 1; i < sz; i ++) {
            nums[empty] = nums[empty + 1];
            empty ++;
        }
    }

    public static void main(String[] args) {
        (new removeElement_27()).removeElement(new int[]{0,1,2,2,3,0,4,2}, 2);
    }
}
