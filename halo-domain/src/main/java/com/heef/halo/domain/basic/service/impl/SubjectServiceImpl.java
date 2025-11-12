package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.dto.authDTO.AuthUserDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectBriefDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectCategoryDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectInfoDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.SubjectLabelDTO;
import com.heef.halo.domain.basic.entity.*;
import com.heef.halo.domain.basic.handler.subject.SubjectTypeHandler;
import com.heef.halo.domain.basic.handler.subject.SubjectTypeHandlerFactory;
import com.heef.halo.domain.basic.mapper.*;
import com.heef.halo.domain.basic.service.ShareService;
import com.heef.halo.domain.basic.service.SubjectService;
import com.heef.halo.domain.convert.SubjectCategoryConvert;
import com.heef.halo.domain.convert.SubjectInfoConvert;
import com.heef.halo.domain.convert.SubjectLabelConvert;
import com.heef.halo.enums.IsDeleteFlagEnum;
import com.heef.halo.result.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private SubjectMappingMapper subjectMappingMapper;

    @Autowired
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    @Autowired
    private SubjectCategoryConvert subjectCategoryConvert;

    @Autowired
    private SubjectLabelConvert subjectLabelConvert;

    @Autowired
    private SubjectInfoConvert subjectInfoConvert;


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

    /**
     * 新增题目
     *
     * @param subjectInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addSubject(SubjectInfoDTO subjectInfoDTO) {
        //1.校验
        if (subjectInfoDTO == null) {
            throw new RuntimeException("题目信息不能为空");
        }
        //2.对象转换
        SubjectInfo subjectInfo = subjectInfoConvert.toInfoEntity(subjectInfoDTO);

        //3.插入数据到题目info总主表
        int inserted = subjectInfoMapper.insert(subjectInfo);

        //4.使用策略工厂处理不同题型插入
        SubjectTypeHandler subjectTypeHandler =subjectTypeHandlerFactory.getHandler(subjectInfoDTO.getSubjectType());
        subjectInfoDTO.setId(Math.toIntExact(subjectInfo.getId()));// 解决题目id没有插入到对应题目类型表的subjectId的问题
        subjectTypeHandler.addSubject(subjectInfo.getId(),subjectInfoDTO);

        //5.获取插入数据的id,来同步到从表(关联表)mapping
        Long subjectInfoId = subjectInfo.getId();

        //6.在mapping处理分类关联
        List<Integer> categoryIds = subjectInfoDTO.getCategoryIds();
        if (!CollectionUtils.isEmpty(categoryIds)) {
            for (Integer categoryId : categoryIds) {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfoId);
                subjectMapping.setCategoryId(Long.valueOf(categoryId));
                subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
                subjectMappingMapper.insert(subjectMapping);
            }
        }
        //7.在mapping处理标签关联
        List<Integer> labelIds = subjectInfoDTO.getLabelIds();
        if (!CollectionUtils.isEmpty(labelIds)) {
            for (Integer labelId : labelIds) {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfoId);
                subjectMapping.setLabelId(Long.valueOf(labelId));
                subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
                subjectMappingMapper.insert(subjectMapping);
            }
        }

        return inserted!=0;
    }


//    /**
//     * 新增题目
//     *
//     * @param subjectInfoDTO
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Boolean addSubject(SubjectInfoDTO subjectInfoDTO) {
//        //1.参数校验
//        if (subjectInfoDTO == null) {
//            throw new RuntimeException("题目信息不能为空");
//        }
//
//        //2.dto转entity
//        SubjectInfo subjectInfo = new SubjectInfo();
//        subjectInfo.setSubjectName(subjectInfoDTO.getSubjectName());
//        subjectInfo.setSubjectDifficult(subjectInfoDTO.getSubjectDifficult());
//        subjectInfo.setSettleName(subjectInfoDTO.getSettleName());
//        subjectInfo.setSubjectType(subjectInfoDTO.getSubjectType());
//        subjectInfo.setSubjectScore(subjectInfoDTO.getSubjectScore());
//        subjectInfo.setSubjectParse(subjectInfoDTO.getSubjectParse());
//        subjectInfo.setSubjectAnswer(subjectInfoDTO.getSubjectAnswer());
//        subjectInfo.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
//
//        //3.插入题目信息
//        subjectInfoMapper.insert(subjectInfo);
//
//        //4.获取题目id
//        Long subjectId = subjectInfo.getId();
//
//        //5.处理分类和标签关联
//        List<Integer> categoryIds = subjectInfoDTO.getCategoryIds();
//        if (!CollectionUtils.isEmpty(categoryIds)) {
//            for (Integer categoryId : categoryIds) {
//                SubjectMapping subjectMapping = new SubjectMapping();
//                subjectMapping.setSubjectId(subjectId);
//                subjectMapping.setCategoryId(Long.valueOf(categoryId));
//                subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
//                subjectMappingMapper.insert(subjectMapping);
//            }
//        }
//
//        List<Integer> labelIds = subjectInfoDTO.getLabelIds();
//        if (!CollectionUtils.isEmpty(labelIds)) {
//            for (Integer labelId : labelIds) {
//                SubjectMapping subjectMapping = new SubjectMapping();
//                subjectMapping.setSubjectId(subjectId);
//                subjectMapping.setLabelId(Long.valueOf(labelId));
//                subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
//                subjectMappingMapper.insert(subjectMapping);
//            }
//        }
//
//        //6.使用策略工厂处理不同题型
//        subjectTypeHandlerFactory.getHandler(subjectInfoDTO.getSubjectType()).add(subjectInfo);
//
//        return true;
//    }
}