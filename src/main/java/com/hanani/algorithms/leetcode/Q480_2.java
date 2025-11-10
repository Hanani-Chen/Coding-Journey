package com.hanani.algorithms.leetcode;

import java.util.*;

//https://leetcode.cn/problems/sliding-window-median/description/
public class Q480_2 {
    //ms = [1,3,-1,-3,5,3,6,7]，以及 k = 3。
//    [1,-1,-1,3,5,6]。2147483647,2147483647
    public static void main(String[] args) {
        Solution solution = new Solution();
        double[] doubles = solution.medianSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
//        double[] doubles = solution.medianSlidingWindow(new int[]{2147483647, 2147483647}, 2);
        System.out.println(Arrays.toString(doubles));

    }
}

class Solution {
    // 使用TreeMap模拟支持重复元素的有序多重集合（MultiSet）
    // small: 窗口的较小半部分，键为数字，值为该数字出现的次数。使用降序排序，方便获取最大值（即第一个键）
    private TreeMap<Integer, Integer> small = new TreeMap<>(Collections.reverseOrder());
    // large: 窗口的较大半部分，使用自然顺序（升序），方便获取最小值（即第一个键）
    private TreeMap<Integer, Integer> large = new TreeMap<>();
    // 记录small和large中实际有效的元素个数（排除已被标记删除的）
    private int smallSize = 0, largeSize = 0;

    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];

        // 初始化第一个窗口
        for (int i = 0; i < k; i++) {
            addNum(nums[i]);
        }
        result[0] = findMedian(k);

        // 开始滑动窗口
        for (int i = k; i < nums.length; i++) {
            int removeNum = nums[i - k]; // 要移出窗口的元素
            int addNum = nums[i];        // 要新加入窗口的元素

            removeNum(removeNum); // 从平衡树中移除旧元素
            addNum(addNum);       // 向平衡树中添加新元素
            result[i - k + 1] = findMedian(k); // 计算新窗口的中位数
        }
        return result;
    }

    /**
     * 向平衡树中添加一个数字
     */
    private void addNum(int num) {
        // 如果small为空，或者当前数字小于等于small的最大值，则应加入small
        if (small.isEmpty() || num <= small.firstKey()) {
            small.put(num, small.getOrDefault(num, 0) + 1);
            smallSize++;
        } else {
            large.put(num, large.getOrDefault(num, 0) + 1);
            largeSize++;
        }
        // 添加元素后，重新调整两个TreeMap的平衡
        balanceHeaps();
    }

    /**
     * 从平衡树中移除一个数字
     */
    private void removeNum(int num) {
        // 判断数字在哪个部分，然后从对应的TreeMap中移除
        if (small.containsKey(num)) {
            int count = small.get(num);
            if (count == 1) {
                small.remove(num);
            } else {
                small.put(num, count - 1);
            }
            smallSize--;
        } else if (large.containsKey(num)) {
            int count = large.get(num);
            if (count == 1) {
                large.remove(num);
            } else {
                large.put(num, count - 1);
            }
            largeSize--;
        }
        // 移除元素后，重新调整两个TreeMap的平衡
        balanceHeaps();
    }

    /**
     * 调整两个TreeMap的大小，确保平衡条件：
     * 1. smallSize 最多比 largeSize 大1。
     * 2. largeSize 不能大于 smallSize。
     */
    private void balanceHeaps() {
        // 调整情况1：如果small中的元素比large多2个或以上，则需要将small的最大值移到large
        if (smallSize > largeSize + 1) {
            int moveNum = small.firstKey();
            // 从small中移除这个最大值
            int count = small.get(moveNum);
            if (count == 1) {
                small.remove(moveNum);
            } else {
                small.put(moveNum, count - 1);
            }
            smallSize--;
            // 将这个最大值添加到large
            large.put(moveNum, large.getOrDefault(moveNum, 0) + 1);
            largeSize++;
        }
        // 调整情况2：如果large中的元素比small多，则需要将large的最小值移到small
        else if (largeSize > smallSize) {
            int moveNum = large.firstKey();
            // 从large中移除这个最小值
            int count = large.get(moveNum);
            if (count == 1) {
                large.remove(moveNum);
            } else {
                large.put(moveNum, count - 1);
            }
            largeSize--;
            // 将这个最小值添加到small
            small.put(moveNum, small.getOrDefault(moveNum, 0) + 1);
            smallSize++;
        }
    }

    /**
     * 计算当前窗口的中位数
     */
    private double findMedian(int k) {
        // 根据窗口大小k的奇偶性来计算中位数
        if ((k & 1) == 1) {
            // 窗口大小为奇数，中位数是small部分的最大值（因为small元素较多）
            return (double) small.firstKey();
        } else {
            // 窗口大小为偶数，中位数是small的最大值和large的最小值的平均值
            return ((double) small.firstKey() + large.firstKey()) / 2.0;
        }
    }
}
