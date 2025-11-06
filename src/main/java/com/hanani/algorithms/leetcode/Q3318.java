package com.hanani.algorithms.leetcode;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//https://leetcode.cn/problems/find-x-sum-of-all-k-long-subarrays-i/?envType=daily-question&envId=2025-11-06
public class Q3318 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] xSum = solution.findXSum(new int[]{1, 1, 2, 2, 3, 4, 2, 3}, 6, 2);
//        [6,10,12]
        System.out.println(Arrays.toString(xSum));
    }

    static class Solution {
        public int[] findXSum(int[] nums, int k, int x) {
            int[] anss = new int[nums.length - k +1];
            for (int i = 0; i < nums.length - k + 1; i++) {
                int ans = 0;
                HashMap<Integer, Integer> integerIntegerHashMap = new HashMap<>();
                for (int j = 0; j < k; j++) {
                    int index = i + j;
                    int num = nums[index];
                    Integer i1 = integerIntegerHashMap.containsKey(num) ?
                            integerIntegerHashMap.put(num, integerIntegerHashMap.get(num) + 1) :
                            integerIntegerHashMap.put(num, 1);
                }
                int l = 0;
                int[][] ints = new int[100][2];
                for (Map.Entry<Integer, Integer> integerIntegerEntry : integerIntegerHashMap.entrySet()) {
                    Integer key = integerIntegerEntry.getKey();
                    Integer value = integerIntegerEntry.getValue();
                    ints[l][0] = key;
                    ints[l][1] = value;
                    l++;
                }
                Arrays.sort(ints, (a, b) -> a[1] == b[1] ? b[0] - a[0] : b[1] - a[1]);
                for (int j = 0; j < x; j++) {
                    ans += ints[j][0] * ints[j][1];
                }
                anss[i] = ans;
            }
            return anss;
        }
    }
}
