package com.hanani.algorithms.leetcode;

import java.util.*;

//https://leetcode.cn/problems/sliding-window-median/description/
public class Q480_4 {
    //ms = [1,3,-1,-3,5,3,6,7]，以及 k = 3。
//    [1,-1,-1,3,5,6]。2147483647,2147483647
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
//        double[] doubles = solution2.medianSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        double[] doubles = solution.medianSlidingWindow(new int[]{2147483647, 2147483647}, 2);
        System.out.println(Arrays.toString(doubles));

    }

}

class Solution2 {
    private AVLTree<Long> left = new AVLTree<>(Comparator.reverseOrder());
    private AVLTree<Long> right = new AVLTree<>();

    public void addNum(int num) {
        if (left.size() == right.size()) {
            right.insert((long) num);
            Long top = right.getMax();
            right.delete(top);
            left.insert(top);
        }else {
            left.insert((long) num);
            Long top = left.getMax();
            left.delete(top);
            right.insert(top);
        }
    }

    public double findMedian() {
        Long left1 = left.getMax();
        Long right1 = right.getMax();
        if (left1 == null) {
            left1 = 0L;
        }
        if (right1 == null) {
            right1 = 0L;
        }
        if (left.size() == right.size()) {
            return (left1 + right1) /2.0;
        } else {
            return left1;
        }
    }

    public void delete(int num) {
        if (left.search((long)num)) {
            left.delete((long)num);
        } else {
            right.delete((long)num);
        }
        if (left.size() < right.size()) {
            Long top = right.getMax();
            right.delete(top);
            left.insert(top);
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

class AVLTree<T> {

    private Node<T> root;
    private int size;
    private final Comparator<? super T> comparator;

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    private static class Node<T> {
        private T key;
        private int balance;
        private int height;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;

        Node(T k, Node<T> p) {
            key = k;
            parent = p;
        }

        public Integer getBalance() {
            return balance;
        }

        public int getHeight() {
            return height;
        }

        public T getKey() {
            return key;
        }
    }

    /** 获取树的大小 */
    public int size() {
        return size;
    }

    public int height() {
        return root.getHeight();
    }

    public T getRootKey() {
        return root.getKey();
    }

    /** 比较两个 key */
    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<? super T>) a).compareTo(b);
    }

    /**
     * 获取树中的最大值（根据比较器定义）
     * @return 最大值，如果树为空则返回null
     */
    public T getMax() {
        if (root == null) {
            return null;
        }

        Node<T> current = root;

        // 根据比较器的逻辑决定最大值的方向
        // 如果是自然顺序或默认比较器，最大值在最右边
        // 如果是逆序比较器，最大值在最左边
        if (comparator == null || comparator.compare(getLeftmost(root).key, getRightmost(root).key) < 0) {
            // 自然顺序：最大值在最右边
            while (current.right != null) {
                current = current.right;
            }
        } else {
            // 逆序顺序：最大值在最左边
            while (current.left != null) {
                current = current.left;
            }
        }

        return current.key;
    }

    /**
     * 获取树中的最小值（根据比较器定义）
     * @return 最小值，如果树为空则返回null
     */
    public T getMin() {
        if (root == null) {
            return null;
        }

        Node<T> current = root;

        // 根据比较器的逻辑决定最小值的方向
        if (comparator == null || comparator.compare(getLeftmost(root).key, getRightmost(root).key) < 0) {
            // 自然顺序：最小值在最左边
            while (current.left != null) {
                current = current.left;
            }
        } else {
            // 逆序顺序：最小值在最右边
            while (current.right != null) {
                current = current.right;
            }
        }

        return current.key;
    }

    /**
     * 获取最左边的节点（辅助方法）
     */
    private Node<T> getLeftmost(Node<T> node) {
        if (node == null) return null;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * 获取最右边的节点（辅助方法）
     */
    private Node<T> getRightmost(Node<T> node) {
        if (node == null) return null;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * 更通用的方法：根据比较器获取极值
     * @param findMax true表示最大值，false表示最小值
     * @return 极值
     */
    public T getExtreme(boolean findMax) {
        if (root == null) {
            return null;
        }

        // 检测比较器的顺序
        boolean isNaturalOrder = isNaturalOrder();

        if (findMax) {
            return isNaturalOrder ? getRightmost(root).key : getLeftmost(root).key;
        } else {
            return isNaturalOrder ? getLeftmost(root).key : getRightmost(root).key;
        }
    }

    /**
     * 检测比较器是自然顺序还是逆序
     */
    private boolean isNaturalOrder() {
        if (root == null) return true;

        // 创建两个测试元素
        Node<T> leftmost = getLeftmost(root);
        Node<T> rightmost = getRightmost(root);

        if (leftmost == rightmost) {
            // 只有一个节点，无法比较，默认为自然顺序
            return true;
        }

        // 比较最左和最右节点来确定顺序
        int cmp = compare(leftmost.key, rightmost.key);
        return cmp < 0; // 如果最左 < 最右，则是自然顺序
    }
    
    /**
     * Inserts a new key into the AVL tree.
     *
     * @param key the key to be inserted
     * @return {@code true} if the key was inserted, {@code false} if the key already exists
     */
    public boolean insert(T key) {
        if (root == null) {
            root = new Node<>(key, null);
            size++;
        } else {
            Node<T> n = root;
            Node<T> parent;
            while (true) {
                int cmp = compare(key, n.key);

                parent = n;
                boolean goLeft = cmp < 0;
                n = goLeft ? n.left : n.right;

                if (n == null) {
                    if (goLeft) {
                        parent.left = new Node<>(key, parent);
                        rebalance(parent.left);
                    } else {
                        parent.right = new Node<>(key, parent);
                        rebalance(parent.right);
                    }
                    size++;
                    break;
                }
            }
        }
        return true;
    }

    /**
     * Deletes a key from the AVL tree.
     *
     * @param delKey the key to be deleted
     */
    public void delete(T delKey) {
        if (root == null) {
            return;
        }

        Node<T> node = root;
        Node<T> child = root;
        while (child != null) {
            node = child;
            int cmp = compare(delKey, node.key);
            child = cmp >= 0 ? node.right : node.left;
            if (cmp == 0) {
                delete(node);
                size--;
                return;
            }
        }
    }

    private void delete(Node<T> node) {
        if (node.left == null && node.right == null) {
            // Leaf node
            if (node.parent == null) {
                root = null;
            } else {
                Node<T> parent = node.parent;
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                rebalance(parent);
            }
            return;
        }

        // Node has one or two children
        Node<T> child;
        if (node.left != null) {
            child = node.left;
            while (child.right != null) {
                child = child.right;
            }
        } else {
            child = node.right;
            while (child.left != null) {
                child = child.left;
            }
        }
        node.key = child.key;
        delete(child);
    }

    /** 返回所有节点的平衡因子 */
    public List<Integer> returnBalance() {
        List<Integer> balances = new ArrayList<>();
        returnBalance(root, balances);
        return balances;
    }

    private void returnBalance(Node<T> n, List<Integer> balances) {
        if (n != null) {
            returnBalance(n.left, balances);
            balances.add(n.getBalance());
            returnBalance(n.right, balances);
        }
    }

    /** 搜索某个 key */
    public boolean search(T key) {
        Node<T> result = searchHelper(this.root, key);
        return result != null;
    }

    private Node<T> searchHelper(Node<T> root, T key) {
        if (root == null) {
            return null;
        }
        int cmp = compare(key, root.key);
        if (cmp == 0) {
            return root;
        } else if (cmp < 0) {
            return searchHelper(root.left, key);
        } else {
            return searchHelper(root.right, key);
        }
    }

    private void rebalance(Node<T> n) {
        setBalance(n);
        if (n.balance == -2) {
            if (height(n.left.left) >= height(n.left.right)) {
                n = rotateRight(n);
            } else {
                n = rotateLeftThenRight(n);
            }
        } else if (n.balance == 2) {
            if (height(n.right.right) >= height(n.right.left)) {
                n = rotateLeft(n);
            } else {
                n = rotateRightThenLeft(n);
            }
        }

        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            root = n;
        }
    }

    private Node<T> rotateLeft(Node<T> a) {
        Node<T> b = a.right;
        b.parent = a.parent;

        a.right = b.left;
        if (a.right != null) {
            a.right.parent = a;
        }

        b.left = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);
        return b;
    }

    private Node<T> rotateRight(Node<T> a) {
        Node<T> b = a.left;
        b.parent = a.parent;

        a.left = b.right;
        if (a.left != null) {
            a.left.parent = a;
        }

        b.right = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);
        return b;
    }

    private Node<T> rotateLeftThenRight(Node<T> n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }

    private Node<T> rotateRightThenLeft(Node<T> n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }

    private int height(Node<T> n) {
        if (n == null) {
            return -1;
        }
        return n.height;
    }

    private void setBalance(Node<T>... nodes) {
        for (Node<T> n : nodes) {
            reheight(n);
            n.balance = height(n.right) - height(n.left);
        }
    }

    private void reheight(Node<T> node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }
}
