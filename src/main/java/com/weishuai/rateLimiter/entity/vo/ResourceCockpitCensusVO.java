package com.weishuai.rateLimiter.entity.vo;

import com.weishuai.rateLimiter.entity.po.ResourceCockpitCensus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description : 资源相关驾驶舱统计信息VO
 * @Author : Future Buddha
 * @Date: 2022-01-17 09:22
 */
@Setter
@Getter
@ToString
public class ResourceCockpitCensusVO extends ResourceCockpitCensus implements Serializable {

    private static final long serialVersionUID = -5657792953778415634L;

    /**
     * 发展蚂蚁数（发展合生活蚂蚁人数）
     */
    private Long ant;
    /**
     * 发展拓展商家数（合链小B商家）
     */
    private Long extensionShop;
    /**
     * 发展引入货源数（供应商入驻合链的商家+供应商入驻合生活的商家）
     */
    private Long importSource;

    public ResourceCockpitCensusVO() {
        super();
    }

    /**
     * 静态工厂 - 创建ResourcePublicQuotaVO
     * @return
     */
    public static ResourceCockpitCensusVO of() {
        return new ResourceCockpitCensusVO();
    }

    public ResourceCockpitCensusVO(Builder builder) {
        super(builder);
    }

    public static ResourceCockpitCensusVO of(Builder builder) {
        return new ResourceCockpitCensusVO(builder);
    }
}
