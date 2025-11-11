package com.hanani.algorithms.data_structure;

import java.util.Arrays;

class TreeMultimapTest {
    public static void main(String[] args) {

        TreeMultiMap<String> stringTreeMultiMap = new TreeMultiMap<>();
        stringTreeMultiMap.add("2");
        stringTreeMultiMap.add("2");
        stringTreeMultiMap.add("2");
        stringTreeMultiMap.add("123");
        stringTreeMultiMap.add("323");
        System.out.println(Arrays.toString(stringTreeMultiMap.getAll().toArray()));
        TreeMultiMap<Per> perTreeMultiMap = new TreeMultiMap<>();
        perTreeMultiMap.add(new Per("jack", 12));
        perTreeMultiMap.add(new Per("jack", 120));
        perTreeMultiMap.add(new Per("jack", 18));
        for (Per per : perTreeMultiMap.getAll()) {
            System.out.println(per);
        }

    }

    static class Per implements Comparable<Per>{
        private String name;
        private int age;

        public Per(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Per o) {
            if (o.name.equals(this.name)) {
                return Integer.compare(this.age, o.age);
            } else {
                return o.name.compareTo(this.name);
            }
        }

        @Override
        public String toString() {
            return "Per{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
