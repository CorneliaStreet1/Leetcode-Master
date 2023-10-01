package Greedy.New;

public class CandyReward_135 {
    public int candy(int[] ratings) {
        int[] candyNum = new int[ratings.length];
        candyNum[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candyNum[i] = candyNum[i - 1] + 1;
            }else {
                candyNum[i] = 1;
            }
        }
        for (int i = candyNum.length - 2; i >= 0 ; i --) {
            if (ratings[i] > ratings[i + 1]) {
                candyNum[i] = Math.max(candyNum[i], candyNum[i + 1] + 1);
            }
        }
        int sum = 0;
        for (int i : candyNum) {
            sum += i;
        }
        return sum;
    }
}
