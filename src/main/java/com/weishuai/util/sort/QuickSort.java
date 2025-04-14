package com.weishuai.util.sort;

import com.alibaba.fastjson.JSON;

/**
 * 快排 - 从小到大排序
 * @author ws001
 */
public class QuickSort {
    public static void main(String[] args) {
        QuickSort qs = new QuickSort();
        int[] arr = {20, 40, 90, 30, 25};
        int[] sort = qs.quickSort(arr, 0, arr.length - 1);
        System.out.println(JSON.toJSON(sort));
    }

    private int[] quickSort(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int partitionIndex = partition(arr, leftIndex, rightIndex);
            quickSort(arr, leftIndex, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, rightIndex);
        }
        return arr;
    }

    private int partition(int[] arr, int leftIndex, int rightIndex) {
        int pIndex = leftIndex;
        int index = pIndex + 1;
        for (int i = index; i <= rightIndex; i++) {
            if (arr[i] < arr[pIndex]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pIndex, index - 1);
        return index - 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}

