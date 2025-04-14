package com.weishuai.common.exception;

import java.util.Arrays;
import java.util.Optional;

/**
 * 用户级异常
 * @author ws
 */
public enum UserExceptionEnum implements AbstractBaseExceptionEnum {

    USER_SIGN_IN_EXCEPTION("A0200", "用户登陆异常"),
    USER_REQUIRED_PARAM_NON_NULL_EXCEPTION("A0444", "请求必填参数为空"),
    USER_REQUIRED_PARAM_OUT_OF_ALLOWABLE_RANGE_EXCEPTION("A0445", "请求必填参数超出允许值范围"),
    USER_PHONE_NON_AVAILABLE_ERROR_EXCEPTION("A0446", "手机号不合法"),
    USER_CURRENT_PERSON_IS_PUBLIC_ORGANIZATION("A0447", "当前人员属于'取对公组织公司名称'已被关联"),
    USER_CURRENT_PERSON_IS_PERSONNEL_ORGANIZATION("A0448", "当前人员属于'个人组织'"),
    USER_REQUIRE_FILE_FORM_NON_MATCH_EXCEPTION("A0555", "请求文件格式不匹配"),
    USER_REQUIRE_FILE_NON_EXIST_EXCEPTION("A0556", "请求文件路径下的文件不存在"),


    ;

    private final String code;
    private final String reason;
    private String supplementDesc;
    @Deprecated
    private Throwable throwable;

    UserExceptionEnum(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public static UserExceptionEnum getEnum(String code) {
        Optional<UserExceptionEnum> optional = Arrays.stream(UserExceptionEnum.values()).filter(e -> e.code.equals(code)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }


    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public String getSupplementDesc(){
        return this.supplementDesc;
    };

    public UserExceptionEnum detailMsg(String descMsg) {
        this.supplementDesc = descMsg;
        return this;
    }


    @Override
    public UserExceptionEnum assembleThrowable(String val) {
        throwable = new Throwable(val);
        return this;
    }

    @Override
    public Throwable getThrowable() {
        return new Throwable();
    }

}
