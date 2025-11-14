package com.heef.halo.domain.convert;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectAnswerDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectJudgeDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectMultipleDTO;
import com.heef.halo.domain.basic.entity.SubjectJudge;
import com.heef.halo.domain.basic.entity.SubjectMultiple;
import com.heef.halo.domain.basic.entity.SubjectRadio;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 简答题转换器
 *
 * @author heefM
 * @date 2025-11-12
 */
@Mapper(componentModel = "spring")
public interface SubjectMultipleConvert {

    //新增的时候?
    List<SubjectAnswerDTO> multipleToAnswerDTOList(List<SubjectMultiple> subjectMultipleList);

    //每一个选项list是多个answer对象--我们遍历optionList本质还是获取answer(也就是转换的还是answer变成SubjectMultiple)
    SubjectMultiple answerDTOToMultiple(SubjectAnswerDTO subjectAnswerDTO);

    //Multiple dto-->entity
    SubjectMultiple toMultipleEntity(SubjectMultipleDTO subjectMultipleDTO);

    //Multiple entity-->dto
    SubjectMultipleDTO toMultipleDto(SubjectMultiple subjectMultiple);


    //Multiple dtoList-->entityList
    List<SubjectMultiple> toMultipleEntityList(List<SubjectMultipleDTO> subjectMultipleDTOList);


    //Multiple entityList-->dtoList
    List<SubjectMultipleDTO> toMultipleDtoList(List<SubjectMultiple> subjectMultipleList);

}
