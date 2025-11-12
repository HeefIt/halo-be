package com.heef.halo.domain.convert;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectBriefDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.entity.SubjectBrief;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 简答题转换器
 *
 * @author heefM
 * @date 2025-11-12
 */
@Mapper(componentModel = "spring")
public interface SubjectBriefConvert {
    //brief dto-->entity
    SubjectBrief toBriefEntity(SubjectInfoDTO subjectInfoDTO);

    //brief entity-->dto
    SubjectBriefDTO toBriefDto(SubjectInfo subjectInfo);


    //brief dtoList-->entityList
    List<SubjectBrief> toBriefEntityList(List<SubjectInfoDTO> subjectInfoDTOList);


    //brief entityList-->dtoList
    List<SubjectBriefDTO> toBriefDtoList(List<SubjectInfo> subjectInfoList);

}
