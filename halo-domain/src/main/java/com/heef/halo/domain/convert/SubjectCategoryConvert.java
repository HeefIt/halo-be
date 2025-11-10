package com.heef.halo.domain.convert;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectCategoryDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectLabelDTO;
import com.heef.halo.domain.basic.entity.SubjectCategory;
import com.heef.halo.domain.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 题目分类对象-转换器
 *
 * @author heefM
 * @date 2025-11-09
 */
@Mapper(componentModel = "spring")  // 添加这一行,注入到spring的bean对象,通过注入使用
public interface SubjectCategoryConvert {

    //dto-->entity
    SubjectCategory toEntity(SubjectCategoryDTO subjectCategoryDTO);

    //entity-->dto
    SubjectCategoryDTO toDto(SubjectCategory subjectCategory);


    //dtoList-->entityList
    List<SubjectCategory> toEntityList(List<SubjectCategoryDTO> subjectCategoryDTOList);


    //entityList-->dtoList
    List<SubjectCategoryDTO> toDtoList(List<SubjectCategory> subjectCategoryList);
    

}