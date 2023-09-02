package com.hai.tang.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Node root = new Node(new HashMap<>(), false);



    public class Node {
        public Map<Character, Node> childNodeMap;
        public boolean isWord;

        private Node(Map<Character, Node> childNodeMap, boolean isWord) {
            this.childNodeMap = childNodeMap;
            this.isWord = isWord;
        }

        public void setIsWord(boolean isWord) {
            this.isWord = isWord;
        }

        public boolean getIsWord() {
            return isWord;
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
        Map<Character, Node> fatherNodeMap = root.childNodeMap;
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (fatherNodeMap.containsKey(ch)) {
                Node node = fatherNodeMap.get(ch);
                if (i != charArray.length - 1) {
                    if (node.childNodeMap == null) {
                        node.childNodeMap = new HashMap<>();
                    }
                    fatherNodeMap = node.childNodeMap;
                } else {
                    if (!node.getIsWord()) {
                        node.setIsWord(true);
                    }
                }
            } else {
                if (i != charArray.length - 1) {
                    Node nodeNew = new Node(new HashMap<>(), false);
                    fatherNodeMap.put(ch, nodeNew);
                    fatherNodeMap = nodeNew.childNodeMap;
                } else {
                    Node nodeNew = new Node(null, true);
                    fatherNodeMap.put(ch, nodeNew);
                }
            }
        }
    }


    public Node getRoot() {
        return root;
    }


    public List<String> getMatchStr(String inputStr) {
        List<String> matches = new ArrayList<>();
        if (null == root.childNodeMap) {
            return matches;
        }
        Node node = havaWord(inputStr, root, matches);
        if (node != null) {
            getMatchStr(new StringBuilder(inputStr), new StringBuilder(""), matches, node.childNodeMap);
        }
        return matches;
    }

    private void getMatchStr(StringBuilder inputStr, StringBuilder afterStr, List<String> matches, Map<Character, Node> childNodeMap) {
        if (childNodeMap != null) {
            for (Map.Entry<Character, Node> m : childNodeMap.entrySet()) {
                Character key = m.getKey();
                StringBuilder afterStr1 = new StringBuilder(afterStr);
                afterStr1.append(key);

                Node node = m.getValue();
                boolean isWord = node.getIsWord();
                StringBuilder inputStrOriginl = new StringBuilder(inputStr);
                if (isWord) {
                    matches.add(inputStrOriginl.append(afterStr1).toString());
                }

                if (node.childNodeMap != null) {
                    if (!isWord) {
                        getMatchStr(inputStrOriginl.append(afterStr1), new StringBuilder(""), matches, node.childNodeMap);
                    } else {
                        getMatchStr(inputStrOriginl, new StringBuilder(""), matches, node.childNodeMap);
                    }
                }
            }
        }
    }

    /**
     * 判断前缀树上的所有词语，是否有等于 str 的
     */
    public boolean equelWord(String str) {
        if (null == str) {
            return false;
        }
        List<String> list = new ArrayList<>();
        Node node = havaWord(str, root, list);
        if (node != null && list.size() > 0 && str.equals(list.get(0))) {
            return true;
        }
        return false;
    }

    /**
     * 判断前缀树上的所有词语，是否有 str 开头的
     */
    public boolean startWordWith(String str) {
        if (null == str) {
            return false;
        }
        List<String> list = new ArrayList<>();
        Node node = havaWord(str, root, list);
        if (node != null) {
            if (list.size() == 0) {
                return true;
            }
            if (list.size() == 1 && str.equals(list.get(0))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断前缀树上是否有 以 inputStr 开头的词语，
     * -有,则返回 inputStr 最后一个字符的子节点,若最后一个节点没有子节点则返回最后一个字符的节点
     *      若返回的节点不为 null，且 matches 的大小为 0，则返回的是最后一个字符的子节点（即表明 inputStr 是前缀树上的一个词语的开头，如：前缀树上有 “一生一世” 这个词，但 inputStr 是 “一生一”）
     *      若返回的节点不为 null，且 matches 的大小为 1，则返回的是 inputStr 倒数第二个字符的节点（即表明 inputStr 是前缀树上的一个词语，如：前缀树上有 “一生一世” 这个词，但 inputStr 是 “一生一世”）
     * -没有,则返回 null
     */
    private Node havaWord(String inputStr, Node startNode, List<String> matches) {
        char[] charArray = inputStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            Map<Character, Node> childNodeMap = startNode.childNodeMap;
            if (childNodeMap.containsKey(ch)) {
                Node node = childNodeMap.get(ch);
                if (node != null) {
                    if (node.childNodeMap != null) {
                        startNode = node;
                    }
                    if (null != matches && i == charArray.length - 1 && node.isWord) {
                        matches.add(inputStr);
                    }
                }
            } else {
                return null;
            }
        }
        return startNode;
    }
}
