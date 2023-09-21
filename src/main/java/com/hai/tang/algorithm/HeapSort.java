package com.hai.tang.algorithm;

/**
 * 堆排序
 */
public class HeapSort {
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // 建立大根堆
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // 依次将最大值移到数组末尾
        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // 对剩下的元素重新建立大根堆
            heapify(arr, i, 0);
        }
    }

    public static void heapify(int[] arr, int n, int i) {
        int largest = i; // 初始化最大值为根节点
        int left = 2 * i + 1; // 左子节点
        int right = 2 * i + 2; // 右子节点

        // 如果左子节点比根节点大，则更新最大值为左子节点
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // 如果右子节点比根节点大，则更新最大值为右子节点
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // 如果最大值不是根节点，则交换根节点和最大值的位置，并继续对子树进行堆化操作
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapify(arr, n, largest);
        }
    }
}
