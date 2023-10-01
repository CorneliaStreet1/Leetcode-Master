package Greedy.New;

public class LemonadeChange_860 {
    public boolean lemonadeChange(int[] bills) {
        int Five = 0, Ten = 0, Twenty = 0;
        for (int i = 0; i < bills.length; i++) {
            if (bills[i] == 5) {
                Five ++;
            }
            else if (bills[i] == 10) {
                Five --;
                Ten ++;
                if (Five < 0) {
                    return false;
                }
            }
            else {
                Twenty ++;
                if (!((Five >= 3) || (Five >= 1 && Ten >= 1))) {
                    // 找3张5块的,或者 5 + 10.其他情况没办法正确找零,返回false。
                    return false;
                }else {
                    if (Five >= 3 && Ten == 0) {
                        Five -= 3;
                    }
                    if (Five >= 1 && Ten >= 1) {
                        Five --;
                        Ten --;
                    }
                }
            }
        }
        return true;
    }
}
