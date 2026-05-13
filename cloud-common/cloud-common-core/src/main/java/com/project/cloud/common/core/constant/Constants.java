package com.project.cloud.common.core.constant;

public class Constants {

    public static final String UTF8 = "UTF-8";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String LOGIN_USER_ID = "user_id";

    public static final String LOGIN_USERNAME = "username";

    public static final String LOGIN_AUTHORITIES = "authorities";

    public static final Integer SUCCESS_CODE = 200;

    public static final String SUCCESS_MSG = "操作成功";

    public static final Integer ERROR_CODE = 500;

    public static final String ERROR_MSG = "系统异常，请联系管理员";

    public static final Integer UNAUTHORIZED_CODE = 401;

    public static final String UNAUTHORIZED_MSG = "认证失败，请重新登录";

    public static final Integer FORBIDDEN_CODE = 403;

    public static final String FORBIDDEN_MSG = "没有权限，请联系管理员";

    public static final String TOKEN_KEY = "auth:token:";

    public static final String PERMS_KEY = "auth:perms:";

    public static final String DICT_KEY = "dict:";

    public static final String CAPTCHA_KEY = "captcha:";

    public static final String RATE_KEY = "rate:";

    public static final String LOCK_KEY = "lock:";

    public static final Integer STATUS_ENABLE = 0;

    public static final Integer STATUS_DISABLE = 1;

    public static final Integer NOT_DELETED = 0;

    public static final Integer DELETED = 1;

    private Constants() {
    }
}
