package com.heef.halo.domain.basic.handler.subject;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectOptionDTO;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.domain.basic.entity.SubjectRadio;
import com.heef.halo.domain.basic.service.SubjectRadioService;
import com.heef.halo.enums.IsDeleteFlagEnum;
import com.heef.halo.enums.SubjectInfoTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 单选题策略类
 *
 * @author heefM
 * @date 2025-04-15 17:25
 */
@Slf4j
@Component
public class RadioTypeHandler implements SubjectTypeHandler {

    @Autowired
    private SubjectRadioService subjectRadioService;

    /**
     * 枚举身份的识别(只干选择题)
     *
     * @return
     */
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    /**
     * 单选题题目插入
     * @param subjectInfoDTO
     */
    @Override
    public void addSubject(Long id,SubjectInfoDTO subjectInfoDTO) {
        log.info("正在新增单选题!!!");
    }

    /**
     * 单选题题目查看详情
     * @param subjectId
     * @return
     */
    @Override
    public SubjectOptionDTO querySubject(int subjectId) {
        return null;
    }

}