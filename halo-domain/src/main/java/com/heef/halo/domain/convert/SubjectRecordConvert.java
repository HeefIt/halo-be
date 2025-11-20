package com.heef.halo.domain.convert;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectRecordDTO;
import com.heef.halo.domain.basic.entity.SubjectRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * (SubjectRecord)转换器
 *
 * @author heef
 * @since 2025-11-20 11:32:33
 */
@Mapper(componentModel = "spring")
public interface SubjectRecordConvert {


    /**
     * SubjectRecord转换为SubjectRecordDTO
     *
     * @param subjectRecord 实体类
     * @return DTO
     */
    SubjectRecordDTO toRecordDto(SubjectRecord subjectRecord);

    /**
     * SubjectRecordDTO转换为SubjectRecord
     *
     * @param subjectRecordDTO DTO
     * @return 实体类
     */
    SubjectRecord toRecordEntity(SubjectRecordDTO subjectRecordDTO);

    /**
     * 实体列表转换为DTO列表
     *
     * @param subjectRecordList 实体类列表
     * @return DTO列表
     */
    List<SubjectRecordDTO> toRecordDtoList(List<SubjectRecord> subjectRecordList);

    /**
     * DTO列表转换为实体列表
     *
     * @param subjectRecordDTOList DTO列表
     * @return 实体类列表
     */
    List<SubjectRecord> toRecordEntityList(List<SubjectRecordDTO> subjectRecordDTOList);
}