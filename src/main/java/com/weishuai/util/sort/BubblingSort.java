package com.weishuai.util.sort;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡
 * @author ws001
 */
public class BubblingSort {

    public static void main(String[] args) {
        int[] arr = {20, 40, 90, 30, 25};
        int[] sort = bubbling(arr);
        System.out.println(JSON.toJSON(sort));
    }

    private static int[] bubbling(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    swap(arr, i, j);
                }
            }

        }
        return arr;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
