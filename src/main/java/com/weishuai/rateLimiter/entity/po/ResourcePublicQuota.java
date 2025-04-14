package com.weishuai.rateLimiter.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 资源合伙人对公组织指标信息 - DB
 *
 * @author ws
 */
@Data
public class ResourcePublicQuota implements Serializable {
    private static final long serialVersionUID = -1111345692714145132L;
    /**
     * 对公组织指标信息主键ID
     */
    private Long id;
    /**
     * 资源合伙人公司信息ID
     */
    private Long resourceCompId;
    /**
     * 蚂蚁数（发展合生活蚂蚁人数）
     */
    private Long antNum;
    /**
     * 拓展商家数（合链小B商家）
     */
    private Long extensionShopNum;
    /**
     * 引入货源数（供应商入驻合链的商家+供应商入驻合生活的商家）
     */
    private Long importSourceNum;
    /**
     * 交易金额（正整数 - 万元）
     */
    private Long tradeAmount;
    private Timestamp createTime;
    private Timestamp modifiedTime;
    /**
     * 收益金额 - 元
     */
    private BigDecimal earningsAmount;

    public ResourcePublicQuota() {
    }

    public ResourcePublicQuota(Builder builder) {
        id = builder.id;
        resourceCompId = builder.resourceCompId;
        antNum = builder.antNum;
        extensionShopNum = builder.extensionShopNum;
        importSourceNum = builder.importSourceNum;
        tradeAmount = builder.tradeAmount;
        createTime = builder.createTime;
        modifiedTime = builder.modifiedTime;
        earningsAmount = builder.earningsAmount;
    }

    public static ResourcePublicQuota of() {
        return new ResourcePublicQuota();
    }

    public static ResourcePublicQuota of(Builder builder) {
        return new ResourcePublicQuota(builder);
    }

//    public ResourcePublicQuotaVO entityToVo(ResourcePublicQuota entity) {
//        ResourcePublicQuotaVO vo = ResourcePublicQuotaVO.of();
//        BeanUtils.copyProperties(entity, vo);
//        return vo;
//    }

    public static class Builder {
        private Long id;
        private Long resourceCompId;
        private Long antNum;
        private Long extensionShopNum;
        private Long importSourceNum;
        private Long tradeAmount;
        private Timestamp createTime;
        private Timestamp modifiedTime;
        private BigDecimal earningsAmount;

        public Builder() {
        }

        public static Builder of() {
            return new Builder();
        }

        public ResourcePublicQuota buildQuota() {
            return new ResourcePublicQuota(this);
        }

        public ResourceCockpitCensus buildCensus() {
            return new ResourceCockpitCensus(this);
        }

//        public ResourcePublicQuotaDTO buildDto() {
//            return new ResourcePublicQuotaDTO(this);
//        }
//
//        public ResourcePublicQuotaVO buildVo() {
//            return new ResourcePublicQuotaVO(this);
//        }
//
//        public ResourceCockpitCensusVO buildCockpitCensusVo() {
//            return new ResourceCockpitCensusVO(this);
//        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder resourceCompId(Long resourceCompId) {
            this.resourceCompId = resourceCompId;
            return this;
        }

        public Builder antNum(Long antNum) {
            this.antNum = antNum;
            return this;
        }

        public Builder extensionShopNum(Long extensionShopNum) {
            this.extensionShopNum = extensionShopNum;
            return this;
        }

        public Builder importSourceNum(Long importSourceNum) {
            this.importSourceNum = importSourceNum;
            return this;
        }

        public Builder tradeAmount(Long tradeAmount) {
            this.tradeAmount = tradeAmount;
            return this;
        }

        public Builder createTime(Timestamp createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder modifiedTime(Timestamp modifiedTime) {
            this.modifiedTime = modifiedTime;
            return this;
        }

        public Builder earningsAmount(BigDecimal earningsAmount) {
            this.earningsAmount = earningsAmount;
            return this;
        }
    }
}