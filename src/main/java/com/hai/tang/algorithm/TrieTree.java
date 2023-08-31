package com.hai.tang.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 前缀树
 */
public class TrieTree {

    /**
     * 前缀树总的节点数
     */
    private int size;

    /**
     * 根节点
     */
    private Node root;


    /**
     * 当前节点的下一个节点
     */
    // private Map<Character, Node> nextNode = new HashMap<>();
    private Map<Integer, Map<Character, Node>> everyLeverNode = new HashMap<>();


    public class Node {
        public Character value;
        public Map<Character, Node> childNodeMap;

        public Node(Character value, Map<Character, Node> childNodeMap) {
            this.value = value;
            this.childNodeMap = childNodeMap;
        }
    }

    /**
     * 获取前缀树的总节点数
     */
    public int size() {
        return size;
    }

    /**
     * 向前缀树中增加节点
     */
    public void add(String addValue) {
        char[] charArray = addValue.toCharArray();
        if (null == root) {
            //设置根节点里的值为空字符串
            root = new Node('a', null);
        }
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (null != root) {
                if (null == root.childNodeMap) {
                    Map<Character, Node> firstLeverMap = new HashMap<>();
                    firstLeverMap.put(ch, null);
                    root.childNodeMap = firstLeverMap;

//                    everyLeverNode.put(1, firstLeverMap);
//                    size++;
                } else {
                    add(ch, root.childNodeMap, charArray, 1, i + 1);
                }
            }
        }
    }


    public void add(Character ch, Map<Character, Node> currentLeverMap, char[] charArray, int currentLever, int level) {
        if (null != currentLeverMap) {
            //当前层级不包含则加上
            if (currentLever == level) {
                if (!currentLeverMap.containsKey(ch)) {
                    currentLeverMap.put(ch, null);
//                    everyLeverNode.put(currentLever, currentLeverMap);
//                    size++;
                }
            }
            if (currentLever < level) {
                char currentCh = charArray[currentLever - 1];
                if (currentLeverMap.containsKey(currentCh)) {
                    Node node = currentLeverMap.get(currentCh);
                    if (node == null) {
                        currentLeverMap.put(currentCh, new Node(null , new HashMap<>()));
                        everyLeverNode.put(currentLever + 1, currentLeverMap);
                    }
                }
                add(ch, currentLeverMap.get(currentCh).childNodeMap, charArray, currentLever + 1, level);
            }
        }
    }

    public Node get() {
        return root;
    }
}
