package com.hai.tang.junit5test;

import com.hai.tang.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("mock示例测试类")
@ExtendWith(MockitoExtension.class)
public class MockTest {
    @Mock
    Student student;
    @Mock
    List<String> list;

    @DisplayName("when..thenReturn.. 模拟方法的返回值")
    @Test
    public void mockExample1() {
        //设置 student.getName("张三",1)的返回值是"模拟的张三"，
        when(student.getName("张三", 1)).thenReturn("模拟的张三");
        //调用student.getName("张三",1) 将不会去执行 student.getName()里的逻辑，会直接返回上面设置的 "模拟的张三"
        assertEquals("模拟的张三", student.getName("张三", 1));

        when(list.get(0)).thenReturn("aa");
        assertEquals("aa", list.get(0));
    }

    @DisplayName("verify 验证方法是否被调用")
    @Test
    public void verifyCorrect1() {
        //调用 student.getName
        student.getName("a", 1);
        // 验证模拟的 student 实例其 getName 方法是否被调用过
        verify(student).getName(anyString(), anyInt());
    }


    @DisplayName("verify 验证方法是否被调用")
    @Test
    public void verifyCorrect2() {
        //设置  student.getName 返回值是 “张三”
        when(student.getName(anyString(), anyInt())).thenReturn("张三");
        //调用student.getName并断言
        assertEquals("张三", student.getName("b", 2));
        // 验证模拟的 student 实例其 getName 方法是否被调用过
        verify(student).getName(anyString(), anyInt());
    }

}
