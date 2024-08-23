package com.hai.tang.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("枚举测试类")
public class StatusTest {

    @DisplayName("枚举测试")
    @Test
    public void enumTest() {
        System.out.println(Status.getdesc(0));//查找EventEnum枚举中code是0的description的值。输出：成功
        System.out.println(Status.hasCode(0));//判断EventEnum里是否有为0的code。输出：true
        System.out.println(Status.valueOf("OK").getCode());//查找EventEnum枚举中OK的code。输出：0
        System.out.println(Status.valueOf("OK").getDescription());//查找EventEnum枚举中OK的description。输出：成功

        System.out.println(Status.OK); //输出：OK
        System.out.println(Status.OK.getCode()); //输出:0
        System.out.println(Status.OK.getDescription()); //输出:成功
        System.out.println(Status.getdesc());//输出:[成功, 错误A, 错误B]

        String enumName1 = "aaa";
        String enumName2 = "ERROR_B";
        System.out.println(Status.contains(enumName1));//输出:false。因为EventEnum中不含有 aaa
        System.out.println(Status.contains(enumName2));//输出:true。因为EventEnum中含有 ERROR_B

    }
}
