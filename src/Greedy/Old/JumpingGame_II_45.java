package Greedy.Old;

public class JumpingGame_II_45 {
    public int jump(int[] nums) {
        int Cover = nums[0], Step = 0;
        for (int i = 0; i <= Cover; i ++) {
            int NextCover = nums[i + 1] + i;
            if (Cover >= nums.length - 1) {
                return Step;
            }
            if (i == Cover) {
                Cover = nums[i + 1] + i;
                Step ++;
            }
        }
        return Step;
    }

    public static void main(String[] args) {
        System.out.println((new JumpingGame_II_45()).jump(new int[]{1, 2}));
    }
}
