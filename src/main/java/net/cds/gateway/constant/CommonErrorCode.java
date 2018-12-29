package net.cds.gateway.constant;

/**
 * 公共错误码枚举类
 */
public enum CommonErrorCode {

    INVALID_PARAMATER("InvalidParamater", "参数内容不合法"),

    INVALID_PARAMETER_ISNULL("InvalidParameter.IsNull", "必需参数为空"),

    MISSING_PARAMETER("MissingParameter", "缺少必需参数"),

    THROTTLING("Throttling", "请求被流控"),

    UNSUPPORTED_PARAMETER("UnsupportedParameter", "参数不支持"),

    UNKNOWN_ERROR("UnknownError", "系统未知错误"),

    LAST_TOKEN_PROCESSING("LastTokenProcessing", "ClientToken正在执行, 不能重复提交"),

    UNSUPPORTED_HTTP_METHOD("UnsupportedHTTPMethod", "Http method不支持"),

    INTERNAL_ERROR("InternalError", "系统内部错误"),

    SERVICE_UNAVAILABLE("ServiceUnavailable", "系统服务不可用"),

    SERVICE_UNAVAILABLE_READ_TIMEOUT("ServiceUnavailable.ReadTimeout", "系统服务不可用，读超时"),

    SERVICE_UNAVAILABLE_MAINTAINING("ServiceUnavailable.Maintaining", "系统升级中");


    private String code;
    private String msg;

    CommonErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
