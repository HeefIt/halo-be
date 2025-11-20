package com.heef.halo.domain.basic.service;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectCategoryDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectLabelDTO;
import com.heef.halo.result.PageResult;

import java.util.List;

public interface SubjectService {

    /**
     * 新增题目分类
     *
     * @param subjectCategoryDTO
     */
    Boolean addCategory(SubjectCategoryDTO subjectCategoryDTO);

    /**
     * 分页查询题目分类列表数据
     *
     * @param subjectCategoryDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<SubjectCategoryDTO> selectPage(SubjectCategoryDTO subjectCategoryDTO, Integer pageNum, Integer pageSize);

    /**
     * 修改分类
     *
     * @param id
     * @param subjectCategoryDTO
     * @return
     */
    Boolean update(Long id, SubjectCategoryDTO subjectCategoryDTO);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 查询分类下大类
     *
     * @param subjectCategoryDTO
     * @return
     */
    List<SubjectCategoryDTO> selectCategoryByPrimary(SubjectCategoryDTO subjectCategoryDTO);

    /**
     * 查询分类下大类及其子标签
     *
     * @param subjectCategoryDTO
     * @return
     */
    List<SubjectCategoryDTO> selectCategoryAndLabel(SubjectCategoryDTO subjectCategoryDTO);

    /**
     * 新增题目标签
     *
     * @param subjectLabelDTO
     * @return
     */
    Boolean addLabel(SubjectLabelDTO subjectLabelDTO);

    /**
     * 分页查询题目标签列表数据
     *
     * @param subjectLabelDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<SubjectLabelDTO> selectPageLabel(SubjectLabelDTO subjectLabelDTO, Integer pageNum, Integer pageSize);

    /**
     * 修改标签
     *
     * @param id
     * @param subjectLabelDTO
     * @return
     */
    Boolean updateLabel(Long id, SubjectLabelDTO subjectLabelDTO);

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    Boolean deleteLabel(Long id);

    /**
     * 新增题目
     *
     * @param subjectInfoDTO
     * @return
     */
    Boolean addSubject(SubjectInfoDTO subjectInfoDTO);

    /**
     * 分页查询题目列表
     *
     * @param subjectInfoDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<SubjectInfoDTO> selectSubjectPage(SubjectInfoDTO subjectInfoDTO, Integer pageNum, Integer pageSize);


    /**
     * 分页查询题目列表(面向用户)
     *
     * @param subjectInfoDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<SubjectInfoDTO> selectSubjectPage2(SubjectInfoDTO subjectInfoDTO, Integer pageNum, Integer pageSize);


    /**
     * 查看题目详情
     *
     * @param subjectInfoDTO
     * @return
     */
    SubjectInfoDTO selectSubjectInfo(SubjectInfoDTO subjectInfoDTO);


}