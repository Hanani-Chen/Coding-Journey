package com.hanani.algorithms.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//https://leetcode.cn/problems/sliding-window-median/description/
public class Q480 {
    //ms = [1,3,-1,-3,5,3,6,7]，以及 k = 3。
//    [1,-1,-1,3,5,6]。2147483647,2147483647
    public static void main(String[] args) {
        Solution solution = new Solution();
        double[] doubles = solution.medianSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
//        double[] doubles = solution.medianSlidingWindow(new int[]{2147483647, 2147483647}, 2);
        System.out.println(Arrays.toString(doubles));

    }
    static class Solution {

        private PriorityQueue<Long> left = new PriorityQueue<>(Comparator.reverseOrder());
        private PriorityQueue<Long> right=  new PriorityQueue<>();

        public void addNum(int num) {
            if (left.size() == right.size()) {
                right.add((long) num);
                left.add(right.poll());
            }else {
                left.add((long) num);
                right.add(left.poll());
            }
        }

        public double findMedian() {
            Long left1 = left.peek();
            Long right1 = right.peek();
            if (left.size() == right.size()) {
                return (left1 + right1) /2.0;
            } else {
                return left1;
            }
        }

        public void delete(int num) {
            if (left.contains((long)num)) {
                left.remove((long)num);
            } else {
                right.remove((long)num);
            }
            if (left.size() < right.size()) {
                left.add(right.poll());
            }
        }
        public double[] medianSlidingWindow(int[] nums, int k) {
            double[] doubles = new double[nums.length - k + 1];
            for (int i = 0; i < k; i++) {
                addNum(nums[i]);
            }
            doubles[0] = findMedian();
            for (int i = 0; i < nums.length - k; i++) {
                int removeIndex = i;
                int addIndex = i + k;
                delete(nums[removeIndex]);
                addNum(nums[addIndex]);
                doubles[i+1] = findMedian();
            }
            return doubles;
        }

    }
}
