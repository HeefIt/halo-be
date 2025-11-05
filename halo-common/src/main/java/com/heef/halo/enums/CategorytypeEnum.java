package com.heef.halo.enums;

import lombok.Getter;

@Getter
public enum CategorytypeEnum {
    PRIMARY(1,"岗位大类型"),
    SECONDARY(2,"大类型下类型,二级分类");

    private Integer code;
    private String msg;

    CategorytypeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static CategorytypeEnum getByCode(int codeValue){
        for (CategorytypeEnum ResultCode : CategorytypeEnum.values()) {
            if (ResultCode.code == codeValue){
                return ResultCode;
            }
        }
        return null;
    }
}
