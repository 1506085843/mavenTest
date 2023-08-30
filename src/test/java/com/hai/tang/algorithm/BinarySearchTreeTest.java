package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("二叉搜索树测试类")
public class BinarySearchTreeTest {

    @Test
    @DisplayName("二叉搜索树测试")
    public void binarySearchTest() {
        //新建一个二叉树并向其中插入数据
        BinarySearchTree binaryTree = new BinarySearchTree();
        binaryTree.add(8);
        binaryTree.add(3);
        binaryTree.add(10);
        binaryTree.add(1);
        binaryTree.add(6);
        binaryTree.add(14);
        binaryTree.add(4);
        binaryTree.add(7);
        binaryTree.add(13);

        //输出根节点的值
        BinarySearchTree.Node root = binaryTree.root;
        System.out.println(root.value);
        //输出所有节点数量
        System.out.println(binaryTree.size());
        //前序遍历
        System.out.println(binaryTree.preTraverse());
        //中序遍历
        System.out.println(binaryTree.midTraverse());
        //后序遍历
        System.out.println(binaryTree.afterTraverse());
        //层序遍历
        System.out.println(binaryTree.levelsTraverse());
        //清空二叉搜索树
        binaryTree.clear();
    }
}
