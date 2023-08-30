package com.hai.tang.algorithm;

/**
 * 二分查找
 */
public class BinarySearch {

    public static int search(int search, int[] array) {
        return search(search, array, 0, array.length);
    }

    private static int search(int search, int[] array, int start, int end) {
        int p = (start + end) / 2;
        if (array[p] > search) {
            return search(search, array, start, p);
        } else if (array[p] < search) {
            return search(search, array, p, end);
        }else if (array[p] ==search){
            return p;
        }
        return -1;
    }

}
