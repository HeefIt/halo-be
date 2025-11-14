package com.heef.halo.domain.convert;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectAnswerDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectBriefDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectJudgeDTO;
import com.heef.halo.domain.basic.entity.SubjectBrief;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.domain.basic.entity.SubjectJudge;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 简答题转换器
 *
 * @author heefM
 * @date 2025-11-12
 */
@Mapper(componentModel = "spring")
public interface SubjectJudgeConvert {

    //新增的时候?
    List<SubjectAnswerDTO> judgeToAnswerDTOList(List<SubjectJudge> subjectJudgeList);

    //Judge dto-->entity
    SubjectJudge toJudgeEntity(SubjectJudgeDTO subjectJudgeDTO);

    //Judge entity-->dto
    SubjectJudgeDTO toJudgeDto(SubjectJudge subjectJudge);


    //Judge dtoList-->entityList
    List<SubjectJudge> toJudgeEntityList(List<SubjectJudgeDTO> subjectJudgeDTOList);


    //Judge entityList-->dtoList
    List<SubjectJudgeDTO> toJudgeDtoList(List<SubjectJudge> subjectJudgeList);

}
