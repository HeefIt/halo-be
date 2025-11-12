package com.heef.halo.domain.convert;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectInfoConvert {
    //info dto-->entity
    SubjectInfo toInfoEntity(SubjectInfoDTO subjectInfoDTO);

    //info entity-->dto
    SubjectInfoDTO toInfoDto(SubjectInfo subjectInfo);


    //info dtoList-->entityList
    List<SubjectInfo> toInfoEntityList(List<SubjectInfoDTO> subjectInfoDTOList);


    //info entityList-->dtoList
    List<SubjectInfoDTO> toInfoDtoList(List<SubjectInfo> subjectInfoList);

}
