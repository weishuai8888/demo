package com.weishuai.algorithm.design.pattern.creational.singleton;

/**
 * 饿汉式
 *  - 资源浪费
 *  - 线程安全
 * @author ws001
 */
public class EagerSingleton {

    /**
     * final修饰保证实例的不可变性，提高线程安全性
     */
    private static final EagerSingleton instance = new EagerSingleton();

    /**
     * 反之对象在外部被实例化
     */
    private EagerSingleton() {
    }

    public static EagerSingleton getInstance(){
        return instance;
    }
}
