package com.weishuai.rateLimiter.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Description : 资源合伙人引入供应商导出DO
 * @Author : Future Buddha
 * @Date: 2022-02-18 10:27
 */
@Getter
@Setter
public class ExampleParamsDTO implements Serializable {
    private static final long serialVersionUID = 6247520476149331454L;

    /**
     * 需导出的数据
     */
    private List<ExampleDataDTO> voList;

    /**
     * 导出表的列名 - 从左往右的顺序，不能颠倒
     */
    private List<String> rowNames;

    /**
     * 导出表的表名
     */
    private String title;

    /**
     * 导出表sheet的个数
     */
    private Integer sheetNum;

    public ExampleParamsDTO() {
    }

    public ExampleParamsDTO(Builder builder) {
        this.voList = builder.voList;
        this.rowNames = builder.rowNames;
        this.sheetNum = builder.sheetNum;
        this.title = builder.title;
    }

    public static ExampleParamsDTO of() {
        return new ExampleParamsDTO();
    }

    public static class Builder {
        private List<ExampleDataDTO> voList;
        private List<String> rowNames;
        private String title;
        private Integer sheetNum;

        public Builder() {
        }

        public static Builder of() {
            return new Builder();
        }

        public ExampleParamsDTO build() {
            return new ExampleParamsDTO(this);
        }

        public Builder voList(List<ExampleDataDTO> voList) {
            this.voList = voList;
            return this;
        }

        public Builder rowNames(List<String> rowNames) {
            this.rowNames = rowNames;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder sheetNum(Integer sheetNum) {
            this.sheetNum = sheetNum;
            return this;
        }
    }
}
