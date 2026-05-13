package com.project.cloud.common.core.result;

import com.project.cloud.common.core.constant.Constants;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(Constants.SUCCESS_CODE);
        result.setMsg(Constants.SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error() {
        return error(Constants.ERROR_MSG);
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(Constants.ERROR_CODE);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> unauthorized() {
        return error(Constants.UNAUTHORIZED_CODE, Constants.UNAUTHORIZED_MSG);
    }

    public static <T> Result<T> unauthorized(String msg) {
        return error(Constants.UNAUTHORIZED_CODE, msg);
    }

    public static <T> Result<T> forbidden() {
        return error(Constants.FORBIDDEN_CODE, Constants.FORBIDDEN_MSG);
    }

    public static <T> Result<T> forbidden(String msg) {
        return error(Constants.FORBIDDEN_CODE, msg);
    }

    public boolean isSuccess() {
        return this.code == Constants.SUCCESS_CODE;
    }
}