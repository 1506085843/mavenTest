package com.hai.tang.utiltest;

import com.hai.tang.util.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("随机字符串测试类")
public class RandomStringUtilsTest {

    @DisplayName("测试生成随机数")
    @Test
    public void randomString() {
        //生成长度是100的随机字符串
        RandomStringUtils gen = new RandomStringUtils(100, ThreadLocalRandom.current());
        String randomStr1 = gen.getString();
        String randomStr2 = gen.getString();
        assertThat(randomStr1).isNotEqualTo(randomStr2);
        //输出生成的随机字符串 randomStr1
        System.out.println(randomStr1);
        //输出生成的随机字符串 randomStr2
        System.out.println(randomStr2);
    }

}
