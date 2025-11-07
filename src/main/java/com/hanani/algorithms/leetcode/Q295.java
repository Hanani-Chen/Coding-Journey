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
        if (left.size() == right.size()) {
            right.add(num);
            left.add(right.poll());
        }else {
            left.add(num);
            right.add(left.poll());
        }
    }

    public double findMedian() {
        Integer left1 = left.peek();
        Integer right1 = right.peek();
        if (left.size() == right.size()) {
            return (left1 + right1) /2.0;
        } else {
            return left1;
        }
    }

    public void delete(int num) {
        if (left.contains(num)) {
            left.remove(num);
        } else {
            right.remove(num);
        }
        if (left.size() < right.size()) {
            left.add(right.poll());
        }
    }
}
