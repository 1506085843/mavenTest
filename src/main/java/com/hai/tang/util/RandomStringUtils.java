package com.hai.tang.util;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * 用于生成指定长度的随机字符串
 */
public class RandomStringUtils {
    /**
     * 26个大写字母
     */
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 26个小写字母
     */
    public static final String lower = upper.toLowerCase(Locale.ROOT);

    /**
     * 数字
     */
    public static final String digits = "0123456789";

    /**
     * alphanum为26个大写字母+26个小写字母+10个数字。产生的随机字符串从此挑选字符生成
     */
    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public RandomStringUtils(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * 通过字母数字字符串生成器生成随机字符串。
     */
    public RandomStringUtils(int length, Random random) {
        this(length, random, alphanum);
    }

    /**
     * 使用强随机数生成器生成length个随机字符串
     */
    public RandomStringUtils(int length) {
        this(length, new SecureRandom());
    }

    /**
     * 默认生成长度21的字符串
     */
    public RandomStringUtils() {
        this(21);
    }

    /**
     * 生成随机字符串
     */
    public String getString() {
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}