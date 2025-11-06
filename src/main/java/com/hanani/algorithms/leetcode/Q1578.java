package com.hanani.algorithms.leetcode;

public class Q1578 {
//    输入：colors = "abaac", neededTime = [1,2,3,4,5]

    public static void main(String[] args) {
        System.out.println(new Solution().minCost("aabaa",  new int[]{1,2,3,4,1}));
    }

    static class Solution {
        public int minCost(String colors, int[] neededTime) {
            colors = colors + "0";
            char pre = '0';
            boolean same = false;
            int precost = 0;
            int allCost = 0;
            int cost = 0;
            int max = 0;
            for (int i = 0; i < colors.length(); i++) {
                char c = colors.charAt(i);
                if (c != pre) {
                    if (same) {
                        same = false;
                        max = Math.max(max, precost);
                        allCost+=cost-max+precost;
                        cost = 0;
                        max = 0;
                    }
                } else {
                    same = true;
                    cost += precost;
                    max = Math.max(max, precost);
                }
                if (colors.length() - 1 != i) {
                    precost = neededTime[i];
                }
                pre = c;
            }
            return allCost;
        }
//
//    class Solution {
//        public int minCost(String colors, int[] neededTime) {
//            int i = 0, len = colors.length();
//            int ret = 0;
//            while (i < len) {
//                char ch = colors.charAt(i);
//                int maxValue = 0;
//                int sum = 0;
//
//                while (i < len && colors.charAt(i) == ch) {
//                    maxValue = Math.max(maxValue, neededTime[i]);
//                    sum += neededTime[i];
//                    i++;
//                }
//                ret += sum - maxValue;
//            }
//            return ret;
//        }
//    }
//    class Solution {
//        public int minCost(String colors, int[] neededTime) {
//            int n = neededTime.length;
//            int ans = 0;
//            int maxT = 0;
//            for (int i = 0; i < n; i++) {
//                int t = neededTime[i];
//                ans += t;
//                maxT = Math.max(maxT, t);
//                if (i == n - 1 || colors.charAt(i) != colors.charAt(i + 1)) {
//                    // 遍历到了连续同色段的末尾
//                    ans -= maxT; // 保留耗时最大的气球
//                    maxT = 0; // 准备计算下一段的最大耗时
//                }
//            }
//            return ans;
//        }
//    }

    }
}
