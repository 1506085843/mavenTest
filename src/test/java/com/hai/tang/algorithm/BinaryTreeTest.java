package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("完全二叉树测试类")
public class BinaryTreeTest {

    @Test
    @DisplayName("完全二叉树叉树测试")
    public void binaryTreeTest() {
        //新建一个二叉树并向其中插入数据，按照从上往下，从左往右的顺序添加
        BinaryTree<String> binaryTree = new BinaryTree<>();
        binaryTree.add("A");
        binaryTree.add("B");
        binaryTree.add("C");
        binaryTree.add("D");
        binaryTree.add("E");
        binaryTree.add("F");
        binaryTree.add("G");
        binaryTree.add("H");
        binaryTree.add("i");
        binaryTree.add("j");
        binaryTree.add("k");
        binaryTree.add("L");
        binaryTree.add("M");
        binaryTree.add("N");
        binaryTree.add("O");

        //输出根节点的值
        BinaryTree<String>.Node root = binaryTree.root;
        System.out.println(root.value);
        //输出二叉树总的节点数
        System.out.println(binaryTree.size());
        //前序遍历
        System.out.println(binaryTree.preTraverse());
        //中序遍历
        System.out.println(binaryTree.midTraverse());
        //后序遍历
        System.out.println(binaryTree.afterTraverse());
        //层序遍历
        System.out.println(binaryTree.levelsTraverse());
        //清空二叉树
        binaryTree.clear();
    }
}
