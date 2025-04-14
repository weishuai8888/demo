package com.weishuai.common;

import lombok.Getter;

/**
 * @author ws
 * @Date: 2019-08-13 13:59
 * @Description:
 */
@Getter
public enum ResultCode {
    R_2000_SUCCESS("2000", "success"),

    R_5000_SERVER_ERROR("5000", "Server error"),
    ;

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
