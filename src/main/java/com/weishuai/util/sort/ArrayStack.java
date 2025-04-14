package com.weishuai.util.sort;

/**
 * 栈
 * @author ws001
 */
public class ArrayStack {
    /**
     * 栈元素
     */
    private int[] arrStack;
    /**
     * 栈元素个数
     */
    private int count;
    /**
     * 栈大小
     */
    private int n;

    public ArrayStack(int n) {
        this.arrStack = new int[count];
        this.n = n;
        this.count = 0;
    }

    /**
     * 入栈
     * @param param
     * @return
     */
    public boolean push(int param){
        if (count == n) {
            return false;
        }
        arrStack[count++] = param;
        return true;
    }

    /**
     * 出栈
     * @return
     */
    public int pop() throws Exception {
        if (count == 0) {
            throw new Exception("出栈异常");
        }
        return arrStack[--count];
    }

    /**
     * 查询栈容量个数
     * @return
     */
    public int size(){
        return count;
    }


}




























