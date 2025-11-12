package com.heef.halo.domain.basic.service;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectCategoryDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectLabelDTO;
import com.heef.halo.result.PageResult;

public interface SubjectService {

    /**
     * 新增题目分类
     * @param subjectCategoryDTO
     */
    Boolean addCategory(SubjectCategoryDTO subjectCategoryDTO);

    /**
     * 分页查询题目分类列表数据
     * @param subjectCategoryDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<SubjectCategoryDTO> selectPage(SubjectCategoryDTO subjectCategoryDTO, Integer pageNum, Integer pageSize);

    /**
     * 修改分类
     * @param id
     * @param subjectCategoryDTO
     * @return
     */
    Boolean update(Long id, SubjectCategoryDTO subjectCategoryDTO);

    /**
     * 删除分类
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 新增题目标签
     * @param subjectLabelDTO
     * @return
     */
    Boolean addLabel(SubjectLabelDTO subjectLabelDTO);

    /**
     * 分页查询题目标签列表数据
     * @param subjectLabelDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<SubjectLabelDTO> selectPageLabel(SubjectLabelDTO subjectLabelDTO, Integer pageNum, Integer pageSize);

    /**
     * 修改标签
     * @param id
     * @param subjectLabelDTO
     * @return
     */
    Boolean updateLabel(Long id, SubjectLabelDTO subjectLabelDTO);

    /**
     * 删除标签
     * @param id
     * @return
     */
    Boolean deleteLabel(Long id);

    /**
     * 新增题目
     * @param subjectInfoDTO
     * @return
     */
    Boolean addSubject(SubjectInfoDTO subjectInfoDTO);
}