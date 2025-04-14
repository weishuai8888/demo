package com.weishuai.algorithm.design.pattern.creational.strategy;

/**
 * 两数相加
 * @author ws001
 */
public class AdditionStrategy implements CalculationStrategy{
    @Override
    public int calculation(int num1, int num2) {
        return num1 + num2;
    }
}
