package com.weishuai.rateLimiter.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description : 资源合伙人引入供应商监控VO
 * @Author : Future Buddha
 * @Date: 2022-02-17 14:07
 */
@Getter
@Setter
public class ExampleDataDTO implements Serializable {
    private static final long serialVersionUID = 5273151429664334085L;

    /**
     * 日期 - yyyy-MM-dd HH:mm:ss
     */
    private String createTimeFormat;
    /**
     * 店铺开设人
     */
    private String personName;
    /**
     * 业务人
     */
    private String businessPersonName;
    /**
     * 中台人
     */
    private String midPersonName;
    /**
     * 财务人
     */
    private String financialName;
    /**
     * 法务人
     */
    private String legalName;


    public ExampleDataDTO() {
    }

    public static ExampleDataDTO of() {
        return new ExampleDataDTO();
    }

    public ExampleDataDTO(Builder builder) {
        this.createTimeFormat = builder.createTimeFormat;
        this.personName = builder.personName;
        this.businessPersonName = builder.businessPersonName;
        this.midPersonName = builder.midPersonName;
        this.financialName = builder.financialName;
        this.legalName = builder.legalName;
    }


    public static class Builder {
        private String createTimeFormat;
        private String personName;
        private String businessPersonName;
        private String midPersonName;
        private String financialName;
        private String legalName;

        public Builder() {
        }

        public static Builder of() {
            return new Builder();
        }

        public ExampleDataDTO build(){
            return new ExampleDataDTO(this);
        }


        public Builder createTimeFormat(String createTimeFormat) {
            this.createTimeFormat = createTimeFormat;
            return this;
        }

        public Builder personName(String personName) {
            this.personName = personName;
            return this;
        }

        public Builder businessPersonName(String businessPersonName) {
            this.businessPersonName = businessPersonName;
            return this;
        }

        public Builder midPersonName(String midPersonName) {
            this.midPersonName = midPersonName;
            return this;
        }

        public Builder financialName(String financialName) {
            this.financialName = financialName;
            return this;
        }

        public Builder legalName(String legalName) {
            this.legalName = legalName;
            return this;
        }
    }

}


































