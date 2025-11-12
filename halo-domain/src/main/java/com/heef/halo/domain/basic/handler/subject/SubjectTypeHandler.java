package com.heef.halo.domain.basic.handler.subject;


import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectOptionDTO;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.enums.SubjectInfoTypeEnum;

/**
 * 题目类型工厂接口 (这个接口是我们的灵魂: 相当于业务层的接口,负责给策略去实现)
 *
 * @author heefM
 * @date 2025-04-15 17:11
 */
public interface SubjectTypeHandler {

    /**
     * 枚举身份的识别
     *
     * @return
     */
    SubjectInfoTypeEnum getHandlerType();

    /**
     * 接口规范策略类--题目的插入
     */
    void addSubject(Long id,SubjectInfoDTO subjectInfoDTO);

    /**
     * 接口规范策略类--根据题目id查看题目详情
     */
    SubjectOptionDTO querySubject(int subjectId);

}