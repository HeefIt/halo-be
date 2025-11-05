package com.heef.halo.result;

import com.heef.halo.enums.ResultCode;
import lombok.Data;

@Data
public class Result<T> {
    private boolean success;
    private String message;
    private Integer code;
    private T data;

    public static Result ok() {
        Result result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMsg());
        return result;
    }

    public static<T> Result ok(T data) {
        Result result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static Result fail() {
        Result result = new Result<>();
        result.setSuccess(false);
        result.setCode(ResultCode.FAIL.getCode());
        result.setMessage(ResultCode.FAIL.getMsg());
        return result;
    }

    public static<T> Result fail(T data) {
        Result result = new Result<>();
        result.setSuccess(false);
        result.setCode(ResultCode.FAIL.getCode());
        result.setMessage(ResultCode.FAIL.getMsg());
        result.setData(data);
        return result;
    }

}
