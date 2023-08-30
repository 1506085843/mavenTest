package com.hai.tang.algorithm;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉搜索树
 * 即：
 * 节点的左子树仅包含键小于节点键的节点
 * 节点的右子树只包含键大于节点键的节点
 */
public class BinarySearchTree {
    /**
     * 二叉搜索树总的节点数
     */
    private int size;

    /**
     * 根节点
     */
    public Node root;


    public class Node {
        public int value;
        private Node leftNode;
        private Node rightNode;

        private Node(int value, Node leftNode, Node rightNode) {
            this.value = value;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }
    }

    /**
     * 获取二叉搜索树的总节点数
     */
    public int size() {
        return size;
    }

    /**
     * 向二叉搜索树中增加节点
     */
    public void add(int addValue) {
        if (null == root) {
            root = new Node(addValue, null, null);
        } else {
            add(addValue, root);
        }
        size++;
    }

    private void add(int addValue, Node node) {
        if (null != node) {
            int value = node.value;
            if (addValue < value) {
                if (null != node.leftNode) {
                    add(addValue, node.leftNode);
                } else {
                    node.leftNode = new Node(addValue, null, null);
                }
            }
            if (addValue >= value) {
                if (null != node.rightNode) {
                    add(addValue, node.rightNode);
                } else {
                    node.rightNode = new Node(addValue, null, null);
                }
            }
        }
    }


    /**
     * 前序遍历
     */
    public List<Integer> preTraverse() {
        return preTraverse(new ArrayList<>(), root);
    }

    private List<Integer> preTraverse(List<Integer> list, Node node) {
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
    public List<Integer> midTraverse() {
        return midTraverse(new ArrayList<>(), root);
    }

    private List<Integer> midTraverse(List<Integer> list, Node node) {
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
    public List<Integer> afterTraverse() {
        return afterTraverse(new ArrayList<>(), root);
    }

    private List<Integer> afterTraverse(List<Integer> list, Node node) {
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
    public List<Integer> levelsTraverse() {
        return levelsTraverse(new ArrayList<>(), root);
    }

    private List<Integer> levelsTraverse(List<Integer> list, Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (list.size() != size) {
            Node node = queue.poll();
            list.add(node.value);
            if (node.leftNode != null) {
                queue.offer(node.leftNode);
            }
            if (node.rightNode != null) {
                queue.offer(node.rightNode);
            }
        }
        return list;
    }

    /**
     * 清空二叉搜索树
     */
    public void clear() {
        size = 0;
        root = null;
    }

}

