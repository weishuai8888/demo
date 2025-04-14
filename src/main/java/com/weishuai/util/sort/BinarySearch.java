package com.weishuai.util.sort;

import com.alibaba.fastjson.JSON;

/**
 * 二分查找
 *
 * @author ws001
 */
public class BinarySearch {

    static int result = -1;
    static int base = 0;

    public static void main(String[] args) {
        int[] arr = {20, 30, 40, 45, 56, 70, 90, 120};
        int num = 70;
        int index = find(arr, num);
        System.out.println(JSON.toJSON(index));
    }

    /**
     * 二分查找法,在有序数组arr中,查找target
     * - 如果找到target,返回相应的索引index
     * - 如果没有找到target,返回-1
     *
     * @param arr
     * @param target
     * @return
     */
    public static int find(int[] arr, int target) {

        // 在arr[l...r]之中查找target
        int l = 0, r = arr.length - 1;
        while (l <= r) {

            //int mid = (l + r)/2;
            // 防止极端情况下的整形溢出，使用下面的逻辑求出mid
            int mid = l + (r - l) / 2;

            if (arr[mid] == target) {
                return mid;
            }
            if (arr[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return -1;
    }

}
