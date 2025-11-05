package com.heef.halo.enums;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200,"成功"),
    FAIL(500,"失败");

    private Integer code;
    private String msg;

    ResultCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode getByCode(int codeValue){
        for (ResultCode ResultCode : ResultCode.values()) {
            if (ResultCode.code == codeValue){
                return ResultCode;
            }
        }
        return null;
    }
}
