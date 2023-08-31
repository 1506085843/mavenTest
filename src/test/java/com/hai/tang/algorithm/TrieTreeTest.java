package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("前缀树测试类")
public class TrieTreeTest {


    @Test
    @DisplayName("前缀树测试")
    public void trieTree() {
        TrieTree t = new TrieTree();
        String[] array=new String[]{"张三","张五","张留的歌","张四","张四的","张四的歌","张四的歌啊","qqq","李四","李四的","李四的歌"};
        for (String s : array) {
            t.add(s);
        }
        System.out.println(t.get());
    }

}
