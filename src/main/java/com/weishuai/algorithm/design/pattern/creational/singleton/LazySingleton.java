package com.weishuai.algorithm.design.pattern.creational.singleton;

/**
 * 懒汉式
 *  - 延迟加载
 *  - 线程不安全
 * @author ws001
 */
public class LazySingleton {

    /**
     *
     */
    private static LazySingleton instance;

    /**
     * 防止在外部实例化
     */
    private LazySingleton() {
    }

    public static LazySingleton getInstance(){
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
