package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

@DisplayName("前缀树测试类")
public class TrieTreeTest {


    @Test
    @DisplayName("前缀树测试")
    public void trieTree() {
        TrieTree t = new TrieTree();
        //String[] array=new String[]{"张三","张五","张留的歌","张三的歌","张四","张四的","李四付出","张四的歌","李四的歌","张四的歌啊","qqq","李四","李四的","张五是到","李四的改"};
        String[] array=new String[]{"张三的歌","张三","张力","张五","张留的歌","张留","李四付出","李四的歌","张四的歌啊","李四的歌啊","qqq","李四","李四","张五是到","李四的改"};
        System.out.println(array.length);
        for (String s : array) {
            t.add(s);
        }
       // System.out.println(t.getRootNode());
         System.out.println(t.getMatchStr("张"));
         //System.out.println(t.havaCompleteWord("张三"));
         System.out.println(t.equelWord("李四的"));
         System.out.println(t.startWordWith("张四的"));
         System.out.println(t.size());

         String text="接着判断张三的歌张留指针2和指针3张留的张五李四的歌啊之间的字张四的歌啊符是否属于敏感词，假设指针2指向x，由于Root的子节点不存在x，\n"+
                 "那么指针3 = ++指针2，接着往后进行判断，当指针2走到a的时候，由于Root的子节点中存在a，qqq那么指针1指向字节点a，指\n"+
                 "针2不动，指针3往后走一步，指针3指向b，由于b是a的子节点，那么李四付出李四指针1指向节点b，指针3继续往后走，然后发现f不是节点\n";
        System.out.println(t.sensitiveWordReplaceString(text));
        System.out.println();
        System.out.println(t.markWord("张三的歌张力，你今天过得怎么样张力","【","】"));
    }

}
