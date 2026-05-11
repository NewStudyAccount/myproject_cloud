package com.project.cloud.common.core.constant;

/**
 * 通用常量
 */
public class Constants {

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 成功标记
     */
    public static final int SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final int FAIL = 500;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String USER_ID = "user_id";

    /**
     * 用户名
     */
    public static final String USERNAME = "username";

    /**
     * 授权信息
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 请求来源
     */
    public static final String FROM_SOURCE = "from-source";

    /**
     * 内部请求
     */
    public static final String INNER = "inner";

    /**
     * 用户名称
     */
    public static final String SYS_USER = "sys_user";

    /**
     * 角色名称
     */
    public static final String SYS_ROLE = "sys_role";

    /**
     * 菜单名称
     */
    public static final String SYS_MENU = "sys_menu";

    /**
     * 字典名称
     */
    public static final String SYS_DICT = "sys_dict";

    /**
     * 参数名称
     */
    public static final String SYS_CONFIG = "sys_config";

    /**
     * 日志名称
     */
    public static final String SYS_LOG = "sys_log";

    /**
     * 代码生成名称
     */
    public static final String GEN_CONFIG = "gen_config";

    /**
     * 文件存储名称
     */
    public static final String FILE_STORAGE = "file_storage";

    /**
     * 成功消息
     */
    public static final String SUCCESS_MSG = "操作成功";

    /**
     * 失败消息
     */
    public static final String FAIL_MSG = "操作失败";

    /**
     * 登录用户
     */
    public static final String LOGIN_TOKEN = "login_tokens";

    /**
     * 验证码有效期（分钟）
     */
    public static final int CAPTCHA_EXPIRATION = 5;

    /**
     * 令牌有效期（分钟）
     */
    public static final int TOKEN_EXPIRATION = 120;

    /**
     * 登录失败重试次数
     */
    public static final int LOGIN_RETRY_COUNT = 5;

    /**
     * 登录失败锁定时间（分钟）
     */
    public static final int LOGIN_LOCK_TIME = 10;

    private Constants() {
    }
}
