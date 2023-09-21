package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@DisplayName("堆排序测试类")
public class HeapSortTest {

    @DisplayName("堆排序（升序）")
    @Test
    public void hespSort() {
        int[] arr = {10, 7, 8, 9, 1, 8, 5};
        HeapSort.heapSort(arr);
        QuickSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
