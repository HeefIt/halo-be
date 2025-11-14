package com.heef.halo.domain.basic.handler.subject;

import com.alibaba.fastjson.JSON;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectAnswerDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectOptionDTO;
import com.heef.halo.domain.basic.entity.SubjectMultiple;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.domain.basic.service.SubjectMultipleService;
import com.heef.halo.domain.convert.SubjectMultipleConvert;
import com.heef.halo.enums.IsDeleteFlagEnum;
import com.heef.halo.enums.SubjectInfoTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * 多选题策略类
 *
 * @author heefM
 * @date 2025-04-15 17:25
 */
@Component
@Slf4j
public class MultipleTypeHandler implements SubjectTypeHandler {

    @Autowired
    private SubjectMultipleService subjectMultipleService;

    @Autowired
    private SubjectMultipleConvert subjectMultipleConvert;

    /**
     * 枚举身份的识别
     *
     * @return
     */
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.MULTIPLE;
    }

    /**
     * 多选题题目插入
     *
     * @param subjectInfoDTO
     */
    @Override
    public void addSubject(Long id, SubjectInfoDTO subjectInfoDTO) {

        if (log.isInfoEnabled()) {
            log.info("正在新增多选题!!!:{}", JSON.toJSONString(subjectInfoDTO));
        }
        //多选题目的插入
        List<SubjectMultiple> subjectMultipleList = new LinkedList<>();
        subjectInfoDTO.getOptionList().forEach(option -> {

//            这里处理的是optionList，即选项列表。每个选项是一个SubjectAnswerDTO对象，包含：
//            optionType: 选项类型编码（1=A, 2=B等）
//            optionContent: 选项内容
//            isCorrect: 是否正确
//            所以我们需要遍历subjectInfoDTO.getOptionList()，将每个SubjectAnswerBO转换为SubjectMultiple对象。
            SubjectMultiple subjectMultiple = subjectMultipleConvert.answerDTOToMultiple(option);
            subjectMultiple.setSubjectId(id);
            subjectMultiple.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
            subjectMultipleList.add(subjectMultiple);
        });
        subjectMultipleService.InsertBacth(subjectMultipleList);

    }

    /**
     * 多选题题目查看详情
     *
     * @param subjectId
     * @return
     */
    @Override
    public SubjectOptionDTO querySubject(int subjectId) {
        List<SubjectMultiple> subjectMultipleList = subjectMultipleService.queryMultiple(subjectId);

        List<SubjectAnswerDTO> subjectAnswerDTOList = subjectMultipleConvert.multipleToAnswerDTOList(subjectMultipleList);

        SubjectOptionDTO subjectOptionDTO = new SubjectOptionDTO();

        subjectOptionDTO.setOptionList(subjectAnswerDTOList);

        return subjectOptionDTO;
    }

}