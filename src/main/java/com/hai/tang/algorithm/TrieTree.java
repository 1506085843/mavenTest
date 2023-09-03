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
    private Node root = new Node(new HashMap<>(), false);


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

    /**
     * 获取前缀树的词语数
     */
    public Node getRootNode() {
        return root;
    }

    public void clearRootNode() {
        root = new Node(new HashMap<>(), false);
        size = 0;
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
    public void add(String str) {
        if (str == null || str.trim().isEmpty()) {
            return;
        }
        Map<Character, Node> fatherNodeMap = root.childNodeMap;
        int index = 0;
        int addValueLength = str.length();
        while (index != addValueLength) {
            char ch = str.charAt(index);
            if (fatherNodeMap.containsKey(ch)) {
                Node node = fatherNodeMap.get(ch);
                if (index != addValueLength - 1) {
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
                if (index != addValueLength - 1) {
                    Node nodeNew = new Node(new HashMap<>(), false);
                    fatherNodeMap.put(ch, nodeNew);
                    fatherNodeMap = nodeNew.childNodeMap;
                } else {
                    Node nodeNew = new Node(null, true);
                    fatherNodeMap.put(ch, nodeNew);
                    size++;
                }
            }
            index++;
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
     * 从前缀树中删除词语 str
     * 如果 str 每个字符节点下都只有其下一个字符节点，并且最后一个字符节点下没有节点则可以从根节点删除这个词语，否则将词语的最后一个字符的词语标识设置为false
     */
    public void deleteWord(String str) {
        //判断前缀树上是否有该词语
        if (null != str && haveWord(str)) {
            char[] charArray = str.toCharArray();
            boolean canDelete = true;
            Node startNode = root;
            for (int i = 0; i < charArray.length; i++) {
                char ch = charArray[i];
                Map<Character, Node> childNodeMap = startNode.childNodeMap;
                if (i != charArray.length - 1) {
                    Node node = childNodeMap.get(ch);
                    if (node.childNodeMap.size() > 1) {
                        canDelete = false;
                    }
                    startNode = node;
                } else {
                    Map<Character, Node> lastNodoChildMap = startNode.childNodeMap;
                    if (!canDelete) {
                        startNode = lastNodoChildMap.get(ch);
                        break;
                    }
                    if (lastNodoChildMap != null) {
                        Map<Character, Node> childNodeMap1 = lastNodoChildMap.get(ch).childNodeMap;
                        if (null != childNodeMap1) {
                            canDelete = false;
                            startNode = lastNodoChildMap.get(ch);
                        }
//                        else {
//                            canDelete = true;
//                        }
                    }
                }
            }

            if (canDelete) {
                root.childNodeMap.remove(charArray[0]);
            } else {
                startNode.setIsWord(false);
            }
        }
    }

    /**
     * 判断前缀树上的所有词语，词语  str
     */
    public boolean haveWord(String str) {
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
        Node node = havaWord(str, root, list);
        if (node == null) {
            return false;
        }
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
    public String sensitiveWordReplace(String text) {
        return text != null ? markWordAndWordReplace(text, null, null) : text;
    }

    /**
     * 字符串敏感词标记，若 text 文本中的的词语有在前缀树上的，用 strartSymbol和 endSymbol 将其标记
     * 如：
     * 例1：
     * 假如 "张三"、"今天" 这两个词语在前缀树上
     * 当调用 markWord("张三你好，你今天过得怎么样", "【", "】")；
     * 得到的结果是 "【张三】你好，你【今天】过得怎么样"
     * <p>
     * 例2：
     * 若某一个词是另一个词的前缀，匹配时会将其拆分标记
     * 假如 "张三的歌" 、"张三"、"张力" 这三个词语在前缀树上
     * 当调用 markWord("张三的歌张力，你今天过得怎么样张力", "【", "】")；
     * 得到的结果是: "【张三】【的歌】【张力】，你今天过得怎么样【张力】"
     * 因为"张三的歌"这个词在前缀树上先匹配短的词语，即先匹配到 “张三” 。而不是匹配到"张三的歌"
     * 所以"张三的歌"将被拆分为"张三" "的歌"分别标记
     */
    public String markWord(String text, String strartSymbol, String endSymbol) {
        if (text != null && strartSymbol != null && endSymbol != null) {
            return markWordAndWordReplace(text, strartSymbol, endSymbol);
        }
        return text;
    }

    //敏感词替换、敏感词标记通用方法
    private String markWordAndWordReplace(String text, String strartSymbol, String endSymbol) {
        Node startNode = root;
        //isMarkWord 为 true 则为敏感词标记，false 为敏感词替换
        boolean isMarkWord = strartSymbol != null && endSymbol != null;
        //存储最终的结果
        StringBuilder result = new StringBuilder();
        //若为敏感词替换（及isMarkWord为false）temXin用于存放* ，即匹配到一个字符就存入* 。若为敏感词标记，则temXin用于存放匹配的词
        StringBuilder temXin = new StringBuilder();
        //存放匹配到的字符，即匹配到一个字符就存入
        StringBuilder temMatch = new StringBuilder();
        int index = 0;
        while (index != text.length()) {
            char ch = text.charAt(index);
            Map<Character, Node> childNodeMap = startNode.childNodeMap;
            if (childNodeMap.containsKey(ch)) {
                temXin.append(isMarkWord ? ch : "*");
                Node node = childNodeMap.get(ch);
                if (node.childNodeMap != null) {
                    startNode = node;
                }
                if (node.isWord) {
                    appendResuleClearTemp(result, temXin, temMatch, temXin, startNode, strartSymbol, endSymbol);
                } else {
                    temMatch.append(ch);
                }
            } else {
                if (temMatch.length() > 0) {
                    appendResuleClearTemp(result, temMatch, temMatch, temXin, startNode, strartSymbol, endSymbol);
                }
                Map<Character, Node> rootChildNodeMap = root.childNodeMap;
                if (rootChildNodeMap.containsKey(ch)) {
                    temMatch.append(ch);
                    temXin.append(isMarkWord ? ch : "*");
                    startNode = rootChildNodeMap.get(ch);
                } else {
                    result.append(ch);
                }
            }
            index++;
        }
        return result.toString();
    }

    private void appendResuleClearTemp(StringBuilder result, StringBuilder joinBuilder, StringBuilder temMatch, StringBuilder temXin, Node startNode, String strartSymbol, String endSymbol) {
        if (strartSymbol != null) {
            result.append(strartSymbol);
        }
        result.append(joinBuilder);
        if (endSymbol != null) {
            result.append(endSymbol);
        }
        temMatch.setLength(0);
        temXin.setLength(0);
        startNode = root;
    }
}
