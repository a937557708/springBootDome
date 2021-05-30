package com.tjr.test;

import java.io.*;
public class Main {
    public static class Good {
        int v; // 价格
        int p; // 重要度
        int q; // 主件
        int a1 = 0; // 附件1
        int a2 = 0; // 附件2
        public Good(int v, int p, int q) {
            this.v = v;
            this.p = p;
            this.q = q;
        }
        public void setV(int v) {
            this.v = v;
        }
        public void setA1(int a1) {
            this.a1 = a1;
        }
        public void setA2(int a2) {
            this.a2 = a2;
        }
    }
    public static int dw = 100;//加快运行

    public static void main(String[] args) {
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] arr = br.readLine().split(" ");
            int n = Integer.parseInt(arr[0]);
            final int m = Integer.parseInt(arr[1]);
            final Good[] goods = new Good[m + 1];
            boolean flag = true;
            for (int i = 1; i <= m; i++) {
                arr = br.readLine().split(" ");
                int v = Integer.parseInt(arr[0]);
                final int p = Integer.parseInt(arr[1]);
                final int q = Integer.parseInt(arr[2]);
                if (flag) {
                    if (v % dw != 0) {
                        flag = false;
                        dw = 10;
                        for (int j = 1; j < i; j++) {
                            goods[j].setV(goods[j].v * 10);
                        }
                    }
                }
                v = v / dw;
                goods[i] = new Good(v, p, q);
                if (q > 0) {
                    if (goods[q].a1 == 0) {
                        goods[q].setA1(i);
                    } else {
                        goods[q].setA2(i);
                    }
                }
            }
            n = n / dw;
            dp(n, goods);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dp(int n, Good[] goods) {
        final int len = goods.length;
        final int[][] dp = new int[n + 1][len];
        for (int i = 1; i < len; i++) {
            int v = -1, v1 = -1, v2 = -1, v3 = -1, tempDp = -1, tempDp1 = -1, tempDp2 = -1, tempDp3 = -1;
            v = goods[i].v;
            tempDp = v * goods[i].p;
            if (goods[i].a1 != 0 && goods[i].a2 != 0) {
                v3 = v + goods[goods[i].a1].v + goods[goods[i].a2].v;
                tempDp3 = tempDp + goods[goods[i].a1].v * goods[goods[i].a1].p + goods[goods[i].a2].v * goods[goods[i].a2].p;
            }
            if (goods[i].a1 != 0) {
                v1 = v + goods[goods[i].a1].v;
                tempDp1 = tempDp + goods[goods[i].a1].v * goods[goods[i].a1].p;
            }
            if (goods[i].a2 != 0) {
                v2 = v + goods[goods[i].a2].v;
                tempDp2 = tempDp + goods[goods[i].a2].v * goods[goods[i].a2].p;
            }
            for (int j = 1; j < n + 1; j++) {
                if (goods[i].q > 0) {
                    dp[j][i] = dp[j][i - 1];
                } else {
                    dp[j][i] = dp[j][i - 1];
                    if (j >= v && v != -1) dp[j][i] = Math.max(dp[j][i], dp[j - v][i - 1] + tempDp);
                    if (j >= v1 && v1 != -1) dp[j][i] = Math.max(dp[j][i], dp[j - v1][i - 1] + tempDp1);
                    if (j >= v2 && v2 != -1) dp[j][i] = Math.max(dp[j][i], dp[j - v2][i - 1] + tempDp2);
                    if (j >= v3 && v3 != -1) dp[j][i] = Math.max(dp[j][i], dp[j - v3][i - 1] + tempDp3);
                }
            }
        }
        System.out.println(dp[n][goods.length - 1] * dw);
    }
}