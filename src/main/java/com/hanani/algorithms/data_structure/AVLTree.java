package com.hanani.algorithms.data_structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents an AVL Tree, a self-balancing binary search tree.
 * In an AVL tree, the heights of the two child subtrees of any node
 * differ by at most one. If they differ by more than one at any time,
 * rebalancing is performed to restore this property.
 */
public class AVLTree<T> {

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
    }

    /** 获取树的大小 */
    public int size() {
        return size;
    }

    public int height() {
        return root.getHeight();
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
                if (cmp == 0) {
                    return false; // 已存在
                }

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
