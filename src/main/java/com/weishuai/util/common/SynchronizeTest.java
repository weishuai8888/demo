package com.weishuai.util.common;

/**
 * @Description TODO
 * @Author ws
 * @Date 2024/6/28 10:19
 */
public class SynchronizeTest {

    public synchronized void test1() {
        int i = 1;
    }
    public void test2() {
        synchronized(this) {
            int i = 1;
        }
    }
}
