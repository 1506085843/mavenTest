package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("二分查找测试类")
public class BinarySearchTest {

    @Test
    @DisplayName("二分查找测试")
    public void search() {
        int[] arr = {1, 5, 7, 8, 8, 9, 10, 11, 25, 99};

        int index = BinarySearch.search(99, arr);
        System.out.println(index);
    }

}
