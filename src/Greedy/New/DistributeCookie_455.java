package Greedy.New;

import java.util.Arrays;

public class DistributeCookie_455 {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int cookie = 0, kid = 0;
        while (cookie < s.length && kid < g.length) {
            if (s[cookie] >= g[kid]) {
                cookie ++;
                kid ++;
            }else {
                cookie ++;
            }
        }
        return kid;
    }
}
