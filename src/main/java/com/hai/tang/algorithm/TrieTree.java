package com.hai.tang.algorithm;

import java.util.*;

/**
 * 前缀树
 */
public class TrieTree {

    /**
     * 前缀树总的词语数
     */
    private int size;

    /**
     * 根节点
     */
    private final Node root = new Node(new HashMap<>(), false);


    public static class Node {
        private Map<Character, Node> childNodeMap;
        private boolean isWord;

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

        public Map<Character, Node> getChildNodeMap() {
            return childNodeMap;
        }

        public void setChildNodeMap(Map<Character, Node> childNodeMap) {
            this.childNodeMap = childNodeMap;
        }
    }

    public Node getRoot() {
        return root;
    }

    /**
     * 获取前缀树的词语数
     */
    public int size() {
        return size;
    }

    /**
     * 向前缀树中增加节点
     */
    public void add(String[] addValues) {
        if (addValues != null) {
            Arrays.stream(addValues).forEach(this::add);
        }
    }

    /**
     * 向前缀树中增加节点
     */
    public void add(List<String> addValues) {
        if (addValues != null) {
            addValues.forEach(this::add);
        }
    }

    /**
     * 向前缀树中增加节点
     */
    public void add(String addValue) {
        if (addValue == null || addValue.trim().isEmpty()) {
            return;
        }
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
                    if (!node.isWord) {
                        node.setIsWord(true);
                        size++;
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
                    size++;
                }
            }
        }
    }

    /**
     * 返回前缀树上以 inputStr 开头的词语
     */
    public List<String> getMatchStr(String inputStr) {
        List<String> matches = new ArrayList<>();
        if (root.childNodeMap.size() == 0) {
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
        havaWord(str, root, list);
        return list.size() > 0 && str.equals(list.get(0));
    }

    /**
     * 判断前缀树上的所有词语，是否有 str 开头的
     */
    public boolean startWordWith(String str) {
        if (null == str) {
            return false;
        }
        List<String> list = new ArrayList<>();
        havaWord(str, root, list);

        if (list.size() == 0) {
            return true;
        }
        return list.size() == 1 && str.equals(list.get(0));
    }

    /**
     * 判断前缀树上是否有 以 inputStr 开头的词语，
     * -有,则返回 inputStr 最后一个字符的子节点,若最后一个节点没有子节点则返回最后一个字符的节点
     * 若返回的节点不为 null，且 matches 的大小为 0，则返回的是最后一个字符的子节点（即表明 inputStr 是前缀树上的一个词语的开头，如：前缀树上有 “一生一世” 这个词，但 inputStr 是 “一生一”）
     * 若返回的节点不为 null，且 matches 的大小为 1，则返回的是 inputStr 倒数第二个字符的节点（即表明 inputStr 是前缀树上的一个词语，如：前缀树上有 “一生一世” 这个词，但 inputStr 是 “一生一世”）
     * -没有,则返回 null
     */
    private Node havaWord(String inputStr, Node startNode, List<String> matches) {
        char[] charArray = inputStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            Map<Character, Node> childNodeMap = startNode.childNodeMap;
            if (childNodeMap.containsKey(ch)) {
                Node node = childNodeMap.get(ch);
                if (node.childNodeMap != null) {
                    startNode = node;
                }
                if (null != matches && i == charArray.length - 1 && node.isWord) {
                    matches.add(inputStr);
                }
            } else {
                return null;
            }
        }
        return startNode;
    }


    /**
     * 字符串敏感词替换，若 text 文本中的的词语有在前缀树上的，将其替换为 *
     */
    public String sensitiveWordReplaceString(String text) {
        Node startNode = root;
        StringBuilder result = new StringBuilder();//存储最终的结果
        StringBuilder temXin = new StringBuilder();//存放* ，即匹配到一个字符就存入*
        StringBuilder temMatch = new StringBuilder();//存放匹配到的字符，即匹配到一个字符就存入
        int index = 0;
        while (index != text.length()) {
            char ch = text.charAt(index);
            Map<Character, Node> childNodeMap = startNode.childNodeMap;
            if (childNodeMap.containsKey(ch)) {
                temXin.append("*");
                Node node = childNodeMap.get(ch);
                if (node.childNodeMap != null) {
                    startNode = node;
                }
                if (node.isWord) {
                    appendResuleClearTemp(result, temXin, temMatch, temXin, startNode);
                } else {
                    temMatch.append(ch);
                }
            } else {
                if (temMatch.length() > 0) {
                    appendResuleClearTemp(result, temMatch, temMatch, temXin, startNode);
                }
                Map<Character, Node> rootChildNodeMap = root.childNodeMap;
                if (rootChildNodeMap.containsKey(ch)) {
                    temMatch.append(ch);
                    temXin.append("*");
                    startNode = rootChildNodeMap.get(ch);
                } else {
                    result.append(ch);
                }
            }
            index++;
        }
        return result.toString();
    }

    private void appendResuleClearTemp(StringBuilder result, StringBuilder joinBuilder, StringBuilder temMatch, StringBuilder temXin, Node startNode) {
        result.append(joinBuilder);
        temMatch.setLength(0);
        temXin.setLength(0);
        startNode = root;
    }

}
