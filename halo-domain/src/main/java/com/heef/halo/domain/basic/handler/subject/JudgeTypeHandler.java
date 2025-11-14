package com.heef.halo.domain.basic.handler.subject;

import com.alibaba.fastjson.JSON;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectAnswerDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectOptionDTO;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.domain.basic.entity.SubjectJudge;
import com.heef.halo.domain.basic.service.SubjectJudgeService;
import com.heef.halo.domain.convert.SubjectJudgeConvert;
import com.heef.halo.enums.IsDeleteFlagEnum;
import com.heef.halo.enums.SubjectInfoTypeEnum;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JudgeTypeHandler implements SubjectTypeHandler {

    @Autowired
    private SubjectJudgeService subjectJudgeService;

    @Autowired
    private SubjectJudgeConvert subjectJudgeConvert;

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
     *
     * @param subjectInfoDTO
     */
    @Override
    public void addSubject(Long id, SubjectInfoDTO subjectInfoDTO) {
        if (log.isInfoEnabled()) {
            log.info("正在新增判断选题!!!:{}", JSON.toJSONString(subjectInfoDTO));
        }
        //1: 创建判断题对象
        SubjectJudge subjectJudge = new SubjectJudge();
        //2: 获取答案信息 (为什么get0,因为题目只有一个答案。意思是取判断题的第一个答案--optionList中的答案选项只有一个answer)
        SubjectAnswerDTO subjectAnswerDTO = subjectInfoDTO.getOptionList().get(0);
        //3: 设置判断题的字段
        subjectJudge.setSubjectId(id);
        subjectJudge.setIsCorrect(subjectAnswerDTO.getIsCorrect());
        subjectJudge.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
        //4:插入判断题到数据库
        subjectJudgeService.insert(subjectJudge);

    }

    /**
     * 判断题题目查看题目 一个选项对应一个答案类
     *
     * @param subjectId
     * @return
     */
    @Override
    public SubjectOptionDTO querySubject(int subjectId) {
        //查询判断题
        List<SubjectJudge> subjectJudgeList = subjectJudgeService.queryJudge(subjectId);

        //对象转换
        List<SubjectAnswerDTO> subjectAnswerDTOList = subjectJudgeConvert.judgeToAnswerDTOList(subjectJudgeList);

        //封装返回
        SubjectOptionDTO subjectOptionDTO = new SubjectOptionDTO();
        subjectOptionDTO.setOptionList(subjectAnswerDTOList);
        
        return subjectOptionDTO;
    }

}