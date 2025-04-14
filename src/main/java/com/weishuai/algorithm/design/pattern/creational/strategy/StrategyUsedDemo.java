package com.weishuai.algorithm.design.pattern.creational.strategy;

/**
 * 策略模式使用demo
 *  - 避免了条件语句
 *  - 提高了代码可维护性
 *  - 提高了可扩展性
 *
 *  - 类数量增加
 *  - 策略切换开销
 * @author ws001
 */
public class StrategyUsedDemo {

    public static void main(String[] args) {
        CalculationStrategyContext context = new CalculationStrategyContext();

        // 使用加法策略
        context.setStrategy(new AdditionStrategy());
        int resultAddition = context.executeStrategy(5, 3);
        System.out.println("addition result: " + resultAddition);

        // 使用减法策略
        context.setStrategy(new SubtractionStrategy());
        int resultSubtraction = context.executeStrategy(5, 3);
        System.out.println("subtraction result: " + resultSubtraction);
    }
}
