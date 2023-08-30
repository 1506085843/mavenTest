package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("自平衡二叉搜索树测试类")
public class BalanceBSTTest {

    @Test
    @DisplayName("自平衡二叉搜索树测试")
    public void bstTest() {
        BalanceBST binaryTree = new BalanceBST();
        binaryTree.add(1);
        binaryTree.add(2);
        binaryTree.add(3);
        binaryTree.add(4);
        binaryTree.add(5);
        binaryTree.add(6);
        binaryTree.add(7);
        binaryTree.add(8);
        binaryTree.add(9);
        binaryTree.add(10);

        //输出根节点的值
        BalanceBST.Node root = binaryTree.root;
        System.out.println(root.value);
        //检查树是否平衡，不平衡则返回-1，平衡则返回树的高度
        System.out.println(binaryTree.balanceHeight(root));
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
    }
}
