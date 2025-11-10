package com.heef.halo.domain.convert;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectLabelDTO;
import com.heef.halo.domain.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 标签对象转换器--
 *
 * @author heefM
 * @date 2025-11-10
 */
@Mapper(componentModel = "spring")
public interface SubjectLabelConvert {
    //label dto-->entity
    SubjectLabel toLabelEntity(SubjectLabelDTO subjectLabelDTO);

    //label entity-->dto
    SubjectLabelDTO toLabelDto(SubjectLabel subjectLabel);


    //label dtoList-->entityList
    List<SubjectLabel> toLabelEntityList(List<SubjectLabelDTO> subjectLabelDTOList);


    //label entityList-->dtoList
    List<SubjectLabelDTO> toLabelDtoList(List<SubjectLabel> subjectLabelList);
}
