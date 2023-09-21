package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DisplayName("List和数组测试类")
public class ListAndArrayTest {

    @DisplayName("数组翻转（反转）")
    @Test
    public void arrayReverse() {
        int[] arr = {10, 7, 8, 9, 1, 8, 5};
        reverse(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void reverse(int[] arr) {
        int start = 0;
        int end = arr.length - 1;

        while (start < end) {
            // 交换数组的首尾元素
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;

            // 递增 start，递减 end
            start++;
            end--;
        }
    }

    @DisplayName("List翻转（反转）")
    @Test
    public void ListRreverse() {
        List<Integer> list = Arrays.asList(10, 7, 8, 9, 1, 8, 5);
        Collections.reverse(list);
        System.out.println(list);
    }

}
