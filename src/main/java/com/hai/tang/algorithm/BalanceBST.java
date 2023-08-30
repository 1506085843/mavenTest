package com.hai.tang.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 自平衡二叉搜索树
 * 即：
 * 任何节点的左子树和右子树的差不超过一
 * 左右子树都是平衡的
 */
public class BalanceBST {


    /**
     * 平衡二叉搜索树总的节点数
     */
    private int size;

    /**
     * 根节点
     */
    public Node root;


    /**
     * 不平衡二叉搜索树 调整为 平衡二叉搜索树 时用于记录节点数
     */
    private int tempSize;

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
     * 获取平衡二叉搜索树的总节点数
     */
    public int size() {
        return size;
    }

    /**
     * 检查节点的左右子树是否平衡，不平衡则返回-1，平衡则返回树的高度
     */
    public int balanceHeight(Node currentNode) {
        if (currentNode == null) {
            return 0;
        }

        // 检查左子树高度
        int leftSubtreeHeight = balanceHeight(currentNode.leftNode);
        if (leftSubtreeHeight == -1) return -1;

        // 检查右子树高度
        int rightSubtreeHeight = balanceHeight(currentNode.rightNode);
        if (rightSubtreeHeight == -1) return -1;

        //检查左右子树的高度相差是否超过1，超过则不平衡
        if (Math.abs(leftSubtreeHeight - rightSubtreeHeight) > 1) {
            return -1;
        }
        //如果平衡则返回树的高度
        return (Math.max(leftSubtreeHeight, rightSubtreeHeight) + 1);
    }

    /**
     * 向平衡二叉搜索树中增加节点
     */
    public void add(int addValue) {
        add(addValue, true);
    }


    /**
     * 向平衡二叉搜索树中增加节点
     *
     * @param addValue 要添加的节点的值
     * @param convert  是否检查该树是平衡二叉树
     */
    public void add(int addValue, boolean convert) {
        if (null == root) {
            root = new Node(addValue, null, null);
        } else {
            add(addValue, root);
        }
        if (convert) {
            size++;
        }
        //如果高度大于1则不平衡要转为AVL树
        if (convert && balanceHeight(root) == -1) {
            //利用中序遍历是有序的特点得到排好序的所有节点的值
            List<Integer> allNodes = new ArrayList<>();
            midTraverse(allNodes, root);
            //清空二叉树
            root = null;
            //转为平衡二叉树
            convertTONewTree(allNodes, 0, allNodes.size() - 1);
            tempSize = 0;
        }
    }


    /**
     * 将有序的列表元素转化为一个平衡二叉树
     */
    private void convertTONewTree(List<Integer> list, int start, int end) {
        if (tempSize != size && start <= end && end >= 0) {
            int sum = start + end;
            int middle = sum % 2;
            int middleIndex = middle == 0 ? sum / 2 : (sum / 2) + 1;
            int middleValue = list.get(middleIndex);
            add(middleValue, false);
            tempSize++;
            convertTONewTree(list, start, middleIndex - 1);
            convertTONewTree(list, middleIndex + 1, end);
        }

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


