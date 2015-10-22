package com.aliyun.odps;

/**
 * Created by zjmwqx on 10/19/15.
 */
public class LCS {
    public static void main(String ss[]) {
        get("abcdefg", "hbcsefgk");
    }

    public static int get(String s1, String s2) {

        int lcslen = 0;
        int pos_x = -1, pos_y = -1;
        // 初始化二维数组
        int flag[][] = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i < s1.length() + 1; i++) {
            flag[i][0] = 0;
        }
        for (int i = 0; i < s2.length() + 1; i++) {
            flag[0][i] = 0;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    flag[i][j] = flag[i - 1][j - 1] + 1;
                    if (flag[i][j] > lcslen) {
                        lcslen = flag[i][j];
                    }
                } else {
                    flag[i][j] = 0;
                }
            }
        }
        return lcslen;
    }
}
