package com.hanani.algorithms.data_structure;

import java.util.Comparator;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
class AVLTreeTest {
    public static void main(String[] args) {
        TreeSet<Integer> integers = new TreeSet<>(Comparator.reverseOrder());
        integers.add(1);
        integers.add(3123123);
        integers.add(2);
        Integer i = integers.first();
        // 使用自然排序（Integer实现了Comparable）
        AVLTree<Integer> tree1 = new AVLTree<>();
        tree1.insert(10);
        tree1.insert(20);
        tree1.insert(5);
        System.out.println("树大小: " + tree1.size()); // 输出: 树大小: 3
        System.out.println("树高度: " + tree1.height()); // 输出: 树高度: 2
        System.out.println("是否存在: " + tree1.search(10));

        // 使用自定义比较器（逆序）
        AVLTree<Integer> tree2 = new AVLTree<>(Comparator.reverseOrder());
        tree2.insert(10);
        tree2.insert(20);
        tree2.insert(5);


        // 删除操作
        tree1.delete(10);
        System.out.println("删除后大小: " + tree1.size()); // 输出: 删除后大小: 2

    }
}