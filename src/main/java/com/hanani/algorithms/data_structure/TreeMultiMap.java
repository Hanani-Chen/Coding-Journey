package com.hanani.algorithms.data_structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class TreeMultiMap<E extends Comparable<E>> {
    private final TreeMap<E, ArrayList<E>> map;

    public TreeMultiMap() {
        map = new TreeMap<>();
    }

    public TreeMultiMap(Comparator<? super E> comparator) {
        map = new TreeMap<>(comparator);
    }

    // 添加元素
    public void add(E element) {
        if (!map.containsKey(element)) {
            map.put(element, new ArrayList<>());
        }
        map.get(element).add(element);
    }

    // 移除元素（如果存在）
    public boolean remove(E element) {
        if (map.containsKey(element)) {
            boolean removed = map.get(element).remove(element);
            if (map.get(element).isEmpty()) {
                map.remove(element); // 如果该键下的所有值都被移除，则移除键
            }
            return removed;
        }
        return false;
    }

    // 检查元素是否存在
    public boolean contains(E element) {
        return map.containsKey(element) && map.get(element).contains(element);
    }

    // 获取元素数量
    public int count(E element) {
        if (map.containsKey(element)) {
            return map.get(element).size();
        }
        return 0;
    }

    // 获取所有元素（包括重复）作为一个列表
    public ArrayList<E> getAll() {
        ArrayList<E> allElements = new ArrayList<>();
        for (ArrayList<E> list : map.values()) {
            allElements.addAll(list);
        }
        return allElements;
    }
}
