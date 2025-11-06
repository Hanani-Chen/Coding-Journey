package com.hanani.algorithms.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

//https://leetcode.cn/problems/find-median-from-data-stream/description/
public class Q295 {
    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(4);
        System.out.println(medianFinder.findMedian());


    }

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
}

class MedianFinder {

    private PriorityQueue<Integer> left;
    private PriorityQueue<Integer> right;
    public MedianFinder() {
        left = new PriorityQueue<>(Comparator.reverseOrder());
        right = new PriorityQueue<>();

    }

    public void addNum(int num) {
        int size = left.size();
        int size1 = right.size();
        if (size1 == size) {
            left.add(num);
        }else {
            right.add(num);
        }
        Integer big = left.peek();
        Integer small = right.peek();
        if (null == big || null == small) {
            return;
        }
        if (big > small) {
            left.poll();
            right.poll();
            right.add(big);
            left.add(small);
        }
    }

    public double findMedian() {
        int size = left.size();
        int size1 = right.size();
        Integer left1 = left.peek();
        Integer right1 = right.peek();
        if (left1 == null) {
            return 0;
        }
        if (right1 == null) {
            return left1;
        }
        if (size1 == size) {
            return (left1 + right1) /2.0;
        }else {
            return left1;
        }
    }
}
