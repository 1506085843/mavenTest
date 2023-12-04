package com.hai.tang.algorithm;

import com.hai.tang.util.CalculateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("大数加、减、乘测试类")
public class BigNumberCalculateTest {
    @Test
    @DisplayName("大数加、减、乘测试")
    public void calculateTest() {
        String n1 = "1009901999998809979999999999";
        String n2 = "989096189599989309877571236897";
        System.out.println("n1*n2 积为：" + CalculateUtil.mul(n1, n2));
        System.out.println("n1+n2 和为：" + CalculateUtil.sum(n1, n2));
        System.out.println("n1-n2 差为：" + CalculateUtil.sub(n1, n2));

        String n3 = "-95611100007144465320009981999998809979999999999";
        String n4 = "-100011222444669993875456254562";
        System.out.println("n3*n4 积为：" + CalculateUtil.mul(n3, n4));
        System.out.println("n3+n4 和为：" + CalculateUtil.sum(n3, n4));
        System.out.println("n3-n4 差为：" + CalculateUtil.sub(n3, n4));

    }
}
