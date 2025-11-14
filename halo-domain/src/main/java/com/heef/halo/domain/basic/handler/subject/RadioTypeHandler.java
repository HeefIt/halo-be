package com.heef.halo.domain.basic.handler.subject;

import com.alibaba.fastjson.JSON;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectAnswerDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectOptionDTO;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.domain.basic.entity.SubjectRadio;
import com.heef.halo.domain.basic.service.SubjectRadioService;
import com.heef.halo.domain.convert.SubjectRadioConvert;
import com.heef.halo.enums.IsDeleteFlagEnum;
import com.heef.halo.enums.SubjectInfoTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
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

    @Autowired
    private SubjectRadioConvert subjectRadioConvert;

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
     *
     * @param subjectInfoDTO
     */
    @Override
    public void addSubject(Long id, SubjectInfoDTO subjectInfoDTO) {

        if (log.isInfoEnabled()) {
            log.info("正在新增单选题!!!:{}", JSON.toJSONString(subjectInfoDTO));
        }
        //1: 定义一个集合来存放添加的题目
        List<SubjectRadio> subjectRadioList = new LinkedList<>();

        //2: 把题目添加到集合中
        subjectInfoDTO.getOptionList().forEach(option -> {
            SubjectRadio subjectRadio = subjectRadioConvert.answerDTOToRadio(option);
            subjectRadio.setSubjectId(id);
            subjectRadio.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
            subjectRadioList.add(subjectRadio);
        });

        subjectRadioService.InsertBatch(subjectRadioList);
    }

    /**
     * 单选题题目查看详情
     *
     * @param subjectId
     * @return
     */
    @Override
    public SubjectOptionDTO querySubject(int subjectId) {
//        SubjectRadio subjectRadio = new SubjectRadio();
//        subjectRadio.setSubjectId((long) subjectId);

        List<SubjectRadio> subjectRadioList = subjectRadioService.queryRadio(subjectId);

        List<SubjectAnswerDTO> subjectAnswerDTOList = subjectRadioConvert.radioToAnswerDTOList(subjectRadioList);

        SubjectOptionDTO subjectOptionDTO = new SubjectOptionDTO();
        subjectOptionDTO.setOptionList(subjectAnswerDTOList);
        return subjectOptionDTO;
    }

}