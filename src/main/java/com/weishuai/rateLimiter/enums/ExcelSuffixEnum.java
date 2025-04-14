package com.weishuai.rateLimiter.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @Description : excel后缀枚举
 * @Author : Future Buddha
 * @Date: 2022-02-18 15:33
 */
@Getter
public enum ExcelSuffixEnum {

    /**
     * 2003
     */
    XLS("xls"),
    /**
     * 2007
     */
    XLSX("xlsx"),
    ;
    private String suffix;

    ExcelSuffixEnum(String suffix) {
        this.suffix = suffix;
    }

    public static boolean checkSuffix(String param) {
        return Arrays.stream(ExcelSuffixEnum.values()).anyMatch(suffix -> suffix.suffix.equals(param));
    }
}
