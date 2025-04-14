package com.weishuai.common.exception;

/**
 * 系统级异常
 * @author ws
 */
public enum SystemExceptionEnum implements AbstractBaseExceptionEnum {
    SYSTEM_IO_EXCEPTION("IO异常"),
    SYSTEM_REQUIRED_PARAM_NON_NULL_EXCEPTION("请求必填参数为空"),
    SYSTEM_REQUIRED_PARAM_NON_RIGHTFUL_EXCEPTION("请求必填参数不合法"),
    GET_USER_INFO_EXCEPTION("获取用户信息异常"),
    QUERY_INFO_EXCEPTION("数据库查询异常"),
    SYSTEM_REQUIRED_CHANNEL_INFO_NULL_EXCEPTION("合伙人下没有相关渠道商信息，不存在引入货源收益"),
    SYSTEM_PROTOCOL_ADMINISTRATOR_HAS_EXISTENCE_EXCEPTION("当前渠道商下的补充协议管理人已存在"),
    SYSTEM_DB_DATA_ANOMALOUS_EXCEPTION("数据库数据异常"),
    SYSTEM_CROSS_SERVICE_INVOCATION_EXCEPTION("跨服务调用异常"),
    SYSTEM_EXPORT_FAIL_EXCEPTION("导出失败"),
    SYSTEM_DATA_NON_EXIST_EXCEPTION("数据不存在"),
    SYSTEM_DATA_OUT_OF_GAUGE_EXCEPTION("数据量超过限制值"),

    ;

    private String defaultCode;
    private String reason;
    private String supplementDesc;
    @Deprecated
    private Throwable throwable;

    SystemExceptionEnum(String reason) {
        this.defaultCode = "5000";
        this.reason = reason;
    }



    @Override
    public String getCode() {
        return defaultCode;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public String getSupplementDesc(){
        return this.supplementDesc;
    };

    public SystemExceptionEnum detailMsg(String descMsg) {
        this.supplementDesc = descMsg;
        return this;
    }

    @Override
    public SystemExceptionEnum assembleThrowable(String val) {
        throwable = new Throwable(val);
        return this;
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

}
