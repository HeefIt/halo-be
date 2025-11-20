package com.heef.halo.domain.basic.handler.subject;

import com.alibaba.fastjson.JSON;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectOptionDTO;
import com.heef.halo.domain.basic.entity.SubjectBrief;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.domain.basic.service.SubjectBriefService;
import com.heef.halo.domain.convert.SubjectBriefConvert;
import com.heef.halo.enums.IsDeleteFlagEnum;
import com.heef.halo.enums.SubjectInfoTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 简答题策略类
 *
 * @author heefM
 * @date 2025-04-15 17:25
 */
@Slf4j
@Component
public class BriefTypeHandler implements SubjectTypeHandler {

    @Autowired
    private SubjectBriefService subjectBriefService;

    @Autowired
    private SubjectBriefConvert subjectBriefConvert;

    /**
     * 枚举身份的识别
     *
     * @return
     */
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }

    /**
     * 简答题题目插入
     * @param subjectInfoDTO
     */
    @Override
    public void addSubject(Long id,SubjectInfoDTO subjectInfoDTO) {
        if(log.isInfoEnabled()){
            log.info("正在新增简答题!!!:{}", JSON.toJSONString(subjectInfoDTO));
        }
        //对象转换---存subjectAnswer字段
        //这里可以直接转换是因为SubjectInfoDTO中有subjectAnswer字段，而SubjectBrief实体也有对应的subjectAnswer字段，所以可以直接通过MapStruct进行转换。
        //本质传的就是answer字段
        SubjectBrief subjectBrief = subjectBriefConvert.toBriefEntity(subjectInfoDTO);

        subjectBrief.setSubjectId(id);//这里的id与实体类插入数据库的id不同;主要处理

        subjectBrief.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());

        subjectBriefService.insert(subjectBrief);
    }

    /**
     * 简答题查看题目
     * @param subjectId
     * @return
     */
    @Override
    public SubjectOptionDTO querySubject(int subjectId) {
        //查询简答题
        SubjectBrief subjectBrief=subjectBriefService.queryBrief(subjectId);

        SubjectOptionDTO subjectOptionDTO = new SubjectOptionDTO();
        //组装answer--返回
        subjectOptionDTO.setSubjectAnswer(subjectBrief.getSubjectAnswer());

        return subjectOptionDTO;
    }


}