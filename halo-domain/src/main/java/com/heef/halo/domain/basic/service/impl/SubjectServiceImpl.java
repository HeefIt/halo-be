package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.dto.authDTO.AuthUserDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectBriefDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectCategoryDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectLabelDTO;
import com.heef.halo.domain.basic.entity.AuthUser;
import com.heef.halo.domain.basic.entity.SubjectCategory;
import com.heef.halo.domain.basic.entity.SubjectLabel;
import com.heef.halo.domain.basic.mapper.*;
import com.heef.halo.domain.basic.service.ShareService;
import com.heef.halo.domain.basic.service.SubjectService;
import com.heef.halo.domain.convert.SubjectCategoryConvert;
import com.heef.halo.domain.convert.SubjectLabelConvert;
import com.heef.halo.result.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * suject-题目业务层
 *
 * @author heefM
 * @date 2025-11-05
 */
@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectInfoMapper subjectInfoMapper;

    @Autowired
    private SubjectCategoryMapper subjectCategoryMapper;

    @Autowired
    private SubjectLabelMapper subjectLabelMapper;

    @Autowired
    private SubjectLikedMapper subjectLikedMapper;

    @Autowired
    private SubjectRadioMapper subjectRadioMapper;

    @Autowired
    private SubjectMultipleMapper subjectMultipleMapper;

    @Autowired
    private SubjectJudgeMapper subjectJudgeMapper;

    @Autowired
    private SubjectBriefMapper subjectBriefMapper;

    @Autowired
    private SubjectCategoryConvert subjectCategoryConvert;

    @Autowired
    private SubjectLabelConvert subjectLabelConvert;


    /**
     * 新增题目分类
     *
     * @param subjectCategoryDTO
     */
    @Override
    public Boolean addCategory(SubjectCategoryDTO subjectCategoryDTO) {
        if (subjectCategoryDTO == null) {
            throw new RuntimeException("分类数据不能为空");
        }
        SubjectCategory subjectCategory = subjectCategoryConvert.toEntity(subjectCategoryDTO);

        subjectCategory.setIsDeleted(0);

        int inserted = subjectCategoryMapper.insert(subjectCategory);

        return inserted != 0;

    }

    /**
     * 分页查询题目分类列表数据
     *
     * @param subjectCategoryDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<SubjectCategoryDTO> selectPage(SubjectCategoryDTO subjectCategoryDTO, Integer pageNum, Integer pageSize) {
        //1.计算偏移量-起始页
        int offset = (pageNum - 1) * pageSize;

        //2.dto转实体
        SubjectCategory subjectCategory = subjectCategoryConvert.toEntity(subjectCategoryDTO);

        //3.查询当前页列表数据
        List<SubjectCategory> categoryList = subjectCategoryMapper.selectPage(subjectCategory, offset, pageSize);

        //4.查询表中总数据
        Long total = subjectCategoryMapper.count(subjectCategory);

        //5.实体集合转dto集合返回
        List<SubjectCategoryDTO> categoryDTOList = subjectCategoryConvert.toDtoList(categoryList);

        //6.封装结果返回
        return PageResult.<SubjectCategoryDTO>builder()
                .pageNo(pageNum)            //当前页码
                .pageSize(pageSize)                 //每页大小
                .total(Math.toIntExact(total))//总记录数
                .result(categoryDTOList)     //数据结果列表
                .build();                          //自动计算总页数,起始,结束位置
    }

    /**
     * 修改分类
     *
     * @param id
     * @param subjectCategoryDTO
     * @return
     */
    @Override
    public Boolean update(Long id, SubjectCategoryDTO subjectCategoryDTO) {
        SubjectCategory subjectCategory = subjectCategoryMapper.selectById(id);
        if (subjectCategory == null) {
            throw new RuntimeException("要修改的分类数据不存在");
        }
        if (subjectCategoryDTO == null) {
            throw new RuntimeException("传递的分类数据不能为空");
        }
        SubjectCategory convertEntity = subjectCategoryConvert.toEntity(subjectCategoryDTO);

        int updated = subjectCategoryMapper.update(convertEntity);
        return updated != 0;
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        int deleted = subjectCategoryMapper.deleteById(id);
        return deleted != 0;
    }


    /**
     * 新增题目标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @Override
    public Boolean addLabel(SubjectLabelDTO subjectLabelDTO) {
        if (subjectLabelDTO == null) {
            throw new RuntimeException("标签数据不能为空");
        }

        // 必填字段校验
        if (subjectLabelDTO.getCategoryId() == null) {
            throw new RuntimeException("分类ID不能为空");
        }
        if (StringUtils.isBlank(subjectLabelDTO.getLabelName())) {
            throw new RuntimeException("标签名称不能为空");
        }

        // 校验分类是否存在
        SubjectCategory category = subjectCategoryMapper.selectById(Long.valueOf(subjectLabelDTO.getCategoryId()));
        if (category == null) {
            throw new RuntimeException("选择的分类不存在");
        }
        SubjectLabel subjectLabel = subjectLabelConvert.toLabelEntity(subjectLabelDTO);
        subjectLabel.setIsDeleted(0);

        int inserted = subjectLabelMapper.insert(subjectLabel);
        return inserted != 0;
    }

    /**
     * 分页查询题目标签列表数据
     *
     * @param subjectLabelDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<SubjectLabelDTO> selectPageLabel(SubjectLabelDTO subjectLabelDTO, Integer pageNum, Integer pageSize) {
        //1.计算偏移量-起始页
        int offset = (pageNum - 1) * pageSize;

        //2.dto转实体
        SubjectLabel subjectLabel = subjectLabelConvert.toLabelEntity(subjectLabelDTO);

        //3.查询当前页列表数据
        List<SubjectLabel> labelList = subjectLabelMapper.selectPage(subjectLabel, offset, pageSize);

        //4.查询表中总数据
        Long total = subjectLabelMapper.count(subjectLabel);

        //5.实体集合转dto集合返回
        List<SubjectLabelDTO> labelDTOList = subjectLabelConvert.toLabelDtoList(labelList);

        //6.封装结果返回
        return PageResult.<SubjectLabelDTO>builder()
                .pageNo(pageNum)
                .pageSize(pageSize)
                .total(Math.toIntExact(total))
                .result(labelDTOList)
                .build();
    }

    /**
     * 修改标签
     *
     * @param id
     * @param subjectLabelDTO
     * @return
     */
    @Override
    public Boolean updateLabel(Long id, SubjectLabelDTO subjectLabelDTO) {
        SubjectLabel subjectLabel = subjectLabelMapper.selectById(id);
        if (subjectLabel == null) {
            throw new RuntimeException("要修改的标签数据不存在");
        }
        if (subjectLabelDTO == null) {
            throw new RuntimeException("传递的标签数据不能为空");
        }

        SubjectLabel convertEntity = subjectLabelConvert.toLabelEntity(subjectLabelDTO);
        convertEntity.setId(id);

        int updated = subjectLabelMapper.update(convertEntity);
        return updated != 0;
    }

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteLabel(Long id) {
        int deleted = subjectLabelMapper.deleteById(id);
        return deleted != 0;
    }
}