package com.hai.tang.algorithm;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 完全二叉树
 */
public class BinaryTree<T> {
    /**
     * 二叉树总的节点数
     */
    private int size;

    /**
     * 根节点
     */
    public Node root;

    /**
     * 记录插入的队列
     */
    private Queue<Node> queue;


    public class Node {
        public T value;
        private Node leftNode;
        private Node rightNode;

        private Node(T value, Node leftNode, Node rightNode) {
            this.value = value;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }
    }

    /**
     * 获取二叉树的总节点数
     */
    public int size() {
        return size;
    }

    /**
     * 向二叉树中增加节点
     */
    public void add(T addValue) {
        Node node = new Node(addValue, null, null);
        if (null == root) {
            root = node;
            queue = new LinkedList<>();
            queue.offer(node);
        } else {
            Node queNode = queue.peek();
            if (null == queNode.leftNode) {
                queNode.leftNode = node;
                queue.offer(queNode.leftNode);
            } else if (null == queNode.rightNode) {
                queNode.rightNode = node;
                queue.poll();
                queue.offer(queNode.rightNode);
            }
        }
        size++;
    }


    /**
     * 前序遍历
     */
    public List<T> preTraverse() {
        return preTraverse(new ArrayList<>(), root);
    }

    private List<T> preTraverse(List<T> list, Node node) {
        if (list.size() != size && null != node) {
            list.add(node.value);
            //遍历左节点
            preTraverse(list, node.leftNode);
            //遍历右节点
            preTraverse(list, node.rightNode);
        }
        return list;
    }


    /**
     * 中序遍历
     */
    public List<T> midTraverse() {
        return midTraverse(new ArrayList<>(), root);
    }

    private List<T> midTraverse(List<T> list, Node node) {
        if (list.size() != size && null != node) {
            //遍历左节点
            midTraverse(list, node.leftNode);
            list.add(node.value);
            //遍历右节点
            midTraverse(list, node.rightNode);
        }
        return list;
    }

    /**
     * 后序遍历
     */
    public List<T> afterTraverse() {
        return afterTraverse(new ArrayList<>(), root);
    }

    private List<T> afterTraverse(List<T> list, Node node) {
        if (list.size() != size && null != node) {
            //遍历左节点
            afterTraverse(list, node.leftNode);
            //遍历右节点
            afterTraverse(list, node.rightNode);
            list.add(node.value);
        }
        return list;
    }

    /**
     * 层序遍历
     */
    public List<T> levelsTraverse() {
        return levelsTraverse(new ArrayList<>(), root);
    }

    private List<T> levelsTraverse(List<T> list, Node root) {
        Queue<Node> queueLevels = new LinkedList<>();
        queueLevels.offer(root);
        while (list.size() != size) {
            Node node = queueLevels.poll();
            list.add(node.value);
            if (node.leftNode != null) {
                queueLevels.offer(node.leftNode);
            }
            if (node.rightNode != null) {
                queueLevels.offer(node.rightNode);
            }
        }
        return list;
    }

    /**
     * 清空二叉树
     */
    public void clear() {
        queue = null;
        size = 0;
        root = null;
    }

}

