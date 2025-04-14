package com.weishuai.algorithm.design.pattern.creational.strategy;

/** 计算策略模式接口
 * @author ws001
 */
public interface CalculationStrategy {
    /** 两数计算
     * @param num1
     * @param num2
     * @return
     */
    int calculation(int num1, int num2);
}
