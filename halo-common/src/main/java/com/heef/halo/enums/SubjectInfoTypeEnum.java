package com.heef.halo.enums;

import lombok.Getter;

/**
 * 题目类型枚举
 */
@Getter
public enum SubjectInfoTypeEnum {
    RADIO(1,"单选题"),
    MULTIPLE(2,"多选题"),
    JUDGE(3,"判断题"),
    BRIEF(4,"简答题");

    private Integer code;
    private String msg;

    SubjectInfoTypeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static SubjectInfoTypeEnum getByCode(int codeValue){
        for (SubjectInfoTypeEnum ResultCode : SubjectInfoTypeEnum.values()) {
            if (ResultCode.code == codeValue){
                return ResultCode;
            }
        }
        return null;
    }
}
