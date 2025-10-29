package com.weishuai;


import java.util.*;

/**
 * @Description TODO
 * @Author ws
 * @Date 2025/8/21 09:07
 */
public class Demo {

    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode Merge (ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) {
            return pHead1 == null ? pHead2 : pHead1;
        }
        if (pHead1.val < pHead2.val) {
            pHead1.next = Merge(pHead1.next, pHead2);
            return pHead1;
        }else {
            pHead2.next = Merge(pHead1, pHead2.next);
            return pHead2;
        }
    }


}


