package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("前缀树测试类")
public class TrieTreeTest {


    @Test
    @DisplayName("前缀树测试")
    public void trieTree() {
        TrieTree t = new TrieTree();
        //String[] array=new String[]{"张三","张五","张留的歌","张三的歌","张四","张四的","李四付出","张四的歌","李四的歌","张四的歌啊","qqq","李四","李四的","张五是到","李四的改"};
        String[] array=new String[]{"张三","张五","张留的歌","张三的歌","张四","张四","李四付出","李四的歌","张四的歌啊","李四的歌啊","qqq","李四","李四","张五是到","李四的改"};
        for (String s : array) {
            t.add(s);
        }
        System.out.println(t.getRoot());
       //  System.out.println(t.getMatchStr("张"));
         //System.out.println(t.havaCompleteWord("张三"));
         System.out.println(t.equelWord("张四的"));
         System.out.println(t.startWordWith("张四的"));

    }

}
