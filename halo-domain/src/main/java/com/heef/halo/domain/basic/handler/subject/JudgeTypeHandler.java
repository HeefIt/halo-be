package com.heef.halo.domain.basic.handler.subject;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectOptionDTO;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.domain.basic.entity.SubjectJudge;
import com.heef.halo.domain.basic.service.SubjectJudgeService;
import com.heef.halo.enums.IsDeleteFlagEnum;
import com.heef.halo.enums.SubjectInfoTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 判断题策略类
 *
 * @author heefM
 * @date 2025-04-15 17:25
 */
@Component
public class JudgeTypeHandler implements SubjectTypeHandler {

    @Autowired
    private SubjectJudgeService subjectJudgeService;

    /**
     * 枚举身份的识别
     *
     * @return
     */
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.JUDGE;
    }

    /**
     * 判断题题目插入
     * @param subjectInfoDTO
     */
    @Override
    public void addSubject(Long id,SubjectInfoDTO subjectInfoDTO) {

    }

    /**
     * 判断题题目查看题目
     * @param subjectId
     * @return
     */
    @Override
    public SubjectOptionDTO querySubject(int subjectId) {
        return null;
    }

}