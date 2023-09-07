package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("前缀树测试类")
public class TrieTreeTest {

    @Test
    @DisplayName("前缀树中增加词语")
    public void add() {
        String[] array = new String[]{"菜鸡","菜狗","张三","张三丰满", "张三丰", "张三丰是狗", "张全", "张力",  "张力懦夫","王五","王五王八", "王六滚蛋","滚蛋","滚"};
        TrieTree trieTree = new TrieTree();
        //将数组里面的所有词语加入到前缀树上
        trieTree.add(array);
    }

    @Test
    @DisplayName("获取前缀树上以某个字符串开头的所有词语")
    public void matchStartWord() {
        String[] array = new String[]{"菜鸡","菜狗","张三","张三丰满", "张三丰", "张三丰是狗", "张全", "张力",  "张力懦夫","王五","王五王八", "王六滚蛋","滚蛋","滚"};
        TrieTree trieTree = new TrieTree();
        //将数组里面的所有词语加入到前缀树上
        trieTree.add(array);

        //匹配前缀树上 "张" 开头的词语有哪些
        List<String> matchResult = trieTree.matchStartWord("张");
        //输出：[张全, 张三, 张三丰, 张三丰满, 张三丰是狗, 张力, 张力懦夫]
        System.out.println(matchResult);
    }

    @Test
    @DisplayName("前缀树中是否有某个词语")
    public void haveWord() {
        String[] array = new String[]{"菜鸡","菜狗","张三","张三丰满", "张三丰", "张三丰是狗", "张全", "张力",  "张力懦夫","王五","王五王八", "王六滚蛋","滚蛋","滚"};
        TrieTree trieTree = new TrieTree();
        //将数组里面的所有词语加入到前缀树上
        trieTree.add(array);

        //判断前缀树上是否有 "张三" 这个词语
        boolean existWord = trieTree.haveWord("张三");
        System.out.println(existWord);  //true

        //判断前缀树上是否有 "张三四" 这个词语
        boolean existWord1 = trieTree.haveWord("张三四");
        System.out.println(existWord1); //false
    }

    @Test
    @DisplayName("判断前缀树上是否有某个字符串开头的词语")
    public void haveStartWord() {
        String[] array = new String[]{"菜鸡","菜狗","张三","张三丰满", "张三丰", "张三丰是狗", "张全", "张力",  "张力懦夫","王五","王五王八", "王六滚蛋","滚蛋","滚"};
        TrieTree trieTree = new TrieTree();
        //将数组里面的所有词语加入到前缀树上
        trieTree.add(array);

        //判断前缀树上是否有 "王六滚" 开头的词语
        boolean haveStar = trieTree.startWordWith("王六滚");
        System.out.println(haveStar); //true

        //判断前缀树上是否有 "王六滚蛋" 开头的词语
        boolean haveStar1 = trieTree.startWordWith("王六滚蛋");
        System.out.println(haveStar1); //true
    }

    @Test
    @DisplayName("从前缀树上删除某个词语")
    public void deleteWord() {
        String[] array = new String[]{"菜鸡","菜狗","张三","张三丰满", "张三丰", "张三丰是狗", "张全", "张力",  "张力懦夫","王五","王五王八", "王六滚蛋","滚蛋","滚"};
        TrieTree trieTree = new TrieTree();
        //将数组里面的所有词语加入到前缀树上
        trieTree.add(array);


        System.out.println("没删除前，前缀树含有"+trieTree.size()+"个词语");
        trieTree.deleteWord("张三");
        System.out.println("删除后，前缀树含有"+trieTree.size()+"个词语");
        System.out.println("删除后，前缀树是否含有词语 \"张三\" ："+trieTree.haveWord("张三"));
    }

    @Test
    @DisplayName("前缀树进行敏感词过滤")
    public void sensitiveWordFilter() {
        String[] array = new String[]{"菜鸡","菜狗","张三","张三丰满", "张三丰", "张三丰是狗", "张全", "张力",  "张力懦夫","王五","王五王八", "王六滚蛋","滚蛋","滚"};
        TrieTree trieTree = new TrieTree();
        //将数组里面的所有词语加入到前缀树上
        trieTree.add(array);

        String text = "小张打游戏很厉害，但是张三打游戏真菜鸡。上次我在游戏里说张三菜狗他就生气了。他说滚，我就滚滚蛋了。\n"+
                "王五斗地主很厉害，上次王五王炸，张三丰就骂他，我们是队友你炸我干嘛，然后骂王五王八，王五听错了以为张三丰骂的，于是回骂张三丰是狗。\n"+
                "然后场面一度失控，旁边的张力也被波及，什么张力懦夫张力滚蛋王五王八王五滚蛋都被乱七八糟的骂！";
        //过滤后的文本
        String textFilter = trieTree.sensitiveWordReplace(text);
        System.out.println(textFilter);
    }

    @Test
    @DisplayName("前缀树对文章、消息进行敏感词标记")
    public void markWord() {
        String[] array = new String[]{"菜鸡","菜狗","张三","张三丰满", "张三丰", "张三丰是狗", "张全", "张力",  "张力懦夫","王五","王五王八", "王六滚蛋","滚蛋","滚"};
        TrieTree trieTree = new TrieTree();
        //将数组里面的所有词语加入到前缀树上
        trieTree.add(array);

        String text = "小张打游戏很厉害，但是张三打游戏真菜鸡。上次我在游戏里说张三菜狗他就生气了。他说滚，我就滚滚蛋了。\n"+
                "王五斗地主很厉害，上次王五王炸，张三丰就骂他，我们是队友你炸我干嘛，然后骂王五王八，王五听错了以为张三丰骂的，于是回骂张三丰是狗。\n"+
                "然后场面一度失控，旁边的张力也被波及，什么张力懦夫张力滚蛋王五王八王五滚蛋都被乱七八糟的骂！";
        //标记后的文本
        String mark = trieTree.markWord(text, "【", "】");
        System.out.println(mark);
    }

}
