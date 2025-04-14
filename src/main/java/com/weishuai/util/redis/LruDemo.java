package com.weishuai.util.redis;


import java.util.Hashtable;

public class LruDemo {
    private int cacheSize;
    private Hashtable nodes;
    private int currentSize;
    private CacheNode head;
    private CacheNode last;

    class CacheNode{
        CacheNode prev;
        CacheNode next;
        Object key;
        Object value;

        public CacheNode() {
        }
    }

    public void put(Object key, Object value){
        CacheNode node = (CacheNode)nodes.get(key);
        if (node == null) {
            if (currentSize == cacheSize) {
                if (last != null) {
                    nodes.remove(last.key);
                }
            }else {
                currentSize++;
            }
            node = new CacheNode();
        }
        node.key = key;
        node.value = value;
        nodes.put(key, node);
        //将最新的放到头节点
    }

    public Object get(Object key){
        CacheNode node = (CacheNode)nodes.get(key);
        if (node != null) {
            // 移到链表头
            moveToHead(node);
            return node.value;
        }
        return null;
    }

    public void moveToHead(CacheNode node){
        if (node == head) {
            return;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (last == node) {
            last = node.prev;
        }
        if (head != null){
            node.next = head;
            head.prev = node;
        }
        head = node;
        node.prev = null;
        if (last == null) {
            last = head;
        }


    }
}
