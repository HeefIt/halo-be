package com.heef.halo.domain.convert;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectAnswerDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectJudgeDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectRadioDTO;
import com.heef.halo.domain.basic.entity.SubjectJudge;
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
public interface SubjectRadioConvert {

    //新增的时候?
    List<SubjectAnswerDTO> radioToAnswerDTOList(List<SubjectRadio> subjectRadioList);

    //每一个选项list是多个answer对象--我们遍历optionList本质还是获取answer(也就是转换的还是answer变成SubjectMultiple)
    SubjectRadio answerDTOToRadio(SubjectAnswerDTO subjectAnswerDTO);

    //Radio dto-->entity
    SubjectRadio toRadioEntity(SubjectRadioDTO subjectRadioDTO);

    //Radio entity-->dto
    SubjectRadioDTO toRadioDto(SubjectRadio subjectRadio);


    //Radio dtoList-->entityList
    List<SubjectRadio> toRadioEntityList(List<SubjectRadioDTO> subjectRadioDTOList);


    //Radio entityList-->dtoList
    List<SubjectRadioDTO> toRadioDtoList(List<SubjectRadio> subjectRadioList);

}
