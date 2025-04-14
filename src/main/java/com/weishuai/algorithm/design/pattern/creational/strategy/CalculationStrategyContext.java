package com.weishuai.algorithm.design.pattern.creational.strategy;

/**
 * 计算策略模式上下文
 * @author ws001
 */
public class CalculationStrategyContext {

    private CalculationStrategy strategy;

    /**
     * 设置策略
     */
    public void setStrategy(CalculationStrategy strategy){
        this.strategy = strategy;
    }

    /**
     * 执行计算
     * @param num1
     * @param num2
     * @return
     */
    public int executeStrategy(int num1, int num2){
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set");
        }
        return strategy.calculation(num1, num2);
    }
}
