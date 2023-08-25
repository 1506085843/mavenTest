package com.hai.tang.algorithm;

public class QuickSort {

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(int[] arr, int low, int high) {
        if (low < high) {
            // pi是分区索引，arr[pi]现在在正确的位置
            int pi = partition(arr, low, high);
            // 对每个分区递归地进行排序
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // 选择高位元素作为pivot
        int pivot = arr[high];
        // 小于pivot的元素的索引
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // 如果当前元素小于pivot，交换元素位置,若要改为降序排序将 < 改为 > 即可
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // 把pivot元素放到它正确的位置
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
