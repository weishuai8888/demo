package com.weishuai.algorithm.design.pattern.creational.singleton;

/**
 * 双重检查锁
 *  - 实现懒加载
 *   - 线程安全
 * @author ws001
 */
public class DoubleCheckSingleton{

    /**
     * static: 变量属于类，可以在任何实例中共享，确保全局可见性；类加载时初始化；静态变量是会在内存中分配一次，可以减少内存的使用。
     * volatile: 确保多线程下的可见性。当一个线程修改了 instance 的值，其他线程能够立即看到修改
     */
    private static volatile DoubleCheckSingleton instance;

    /**
     * 防止对象在外部实例化
     */
    private DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getInstance() {
        // 第一次非空判断，只有实例为null时才能获取锁
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                // 第二次非空判断， 确保实例化时对象还未实例化
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
