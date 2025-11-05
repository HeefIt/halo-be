package com.heef.halo.enums;

import lombok.Getter;

@Getter
public enum IsDeleteFlagEnum {
    DELETE(1,"已删除"),
    UN_DELETE(0,"未删除");

    private Integer code;
    private String msg;

    IsDeleteFlagEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static IsDeleteFlagEnum getByCode(int codeValue){
        for (IsDeleteFlagEnum ResultCode : IsDeleteFlagEnum.values()) {
            if (ResultCode.code == codeValue){
                return ResultCode;
            }
        }
        return null;
    }
}
