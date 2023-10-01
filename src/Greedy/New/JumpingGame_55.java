package Greedy.New;

public class JumpingGame_55 {
    public boolean canJump(int[] nums) {
        int CurrentCover = 0;
        for (int i = 0; i <= CurrentCover && i < nums.length; i++) {
            if (i + nums[i] > CurrentCover) {
                CurrentCover = i + nums[i];
            }
        }
        return CurrentCover >= nums.length - 1;
    }

    public static void main(String[] args) {
        (new JumpingGame_55()).canJump(new int[]{2,3,1,1,4});
    }
}
