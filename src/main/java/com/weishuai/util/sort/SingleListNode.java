package com.weishuai.util.sort;

/**
 * 单链表
 * @author ws001
 */
public class SingleListNode {
    private String val;
    private SingleListNode next;
    private SingleListNode head;

    public SingleListNode(String val) {
        this.val = val;
    }

    /**
     * 头插入
     * @param param
     * @return
     */
    public void addFirst(String param){
        SingleListNode node = new SingleListNode(param);
        node.next = head;
        this.head = node;
    }

    /**
     * 尾插入
     * @param param
     * @return
     */
    public void addLast(String param){
        SingleListNode node = new SingleListNode(param);
        if (head == null) {
            this.head = node;
        }else {
            SingleListNode cur = this.head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
        }
    }

    /**
     * 链表长度
     * @return
     */
    public int size(){
        int size = 0;
        SingleListNode cur = this.head;
        while (cur != null) {
            size++;
            cur = cur.next;
        }
        return size;
    }

    /**
     * 删除
     * @param param
     * @throws Exception
     */
    public void remove(String param) throws Exception {
        if (this.head == null) {
            return;
        }
        if (head.val.equals(param)) {
            this.head = this.head.next;
        }
        while (head.next != null) {
            if (param.equals(head.next.val)) {
                head.next = head.next.next;
                return;
            }
        }
        throw new Exception("参数不存在");

    }
}
