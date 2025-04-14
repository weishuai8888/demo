package com.weishuai.rateLimiter.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description : 资源相关驾驶舱统计信息对象 - 非数据库记录表实体
 * @Author : Future Buddha
 * @Date: 2022-01-17 09:23
 */
@Setter
@Getter
public class ResourceCockpitCensus extends ResourcePublicQuota implements Serializable {

    private static final long serialVersionUID = 331982617972821793L;
    /**
     * 交易金额 - GMV（元）
     */
    private BigDecimal tradeAmountYuan;
    /**
     * 交易单量
     */
    private Long tradeOrder;

    /**
     * 资源相关驾驶舱 子分类
     *  - com.hilife.agent.enums.ResourceCockpitCategoryChildEnum
     */
    private String cockpitCategoryChild;

    /**
     * Demo 类
     */
    private Demo demo;

    public ResourceCockpitCensus() {
    }

    public ResourceCockpitCensus(Builder builder) {
    }

    /**
     * 静态工厂
     * @return
     */
    public static ResourceCockpitCensus of(Builder builder) {
        return new ResourceCockpitCensus(builder);
    }

}






































