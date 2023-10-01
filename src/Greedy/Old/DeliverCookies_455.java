package Greedy.Old;

import java.util.Arrays;

public class DeliverCookies_455 {
    public int findContentChildren(int[] g, int[] s) {
        //g:孩子的胃口,s:饼干大小
        Arrays.sort(g);
        Arrays.sort(s);
        int Kid = 0, Cookie = 0;
        while (Kid < g.length && Cookie < s.length) {
            if (g[Kid] <= s[Cookie]) {
                Kid ++;
                Cookie ++;
            }else {
                Cookie ++;
            }
        }
        return Kid;
    }
}
