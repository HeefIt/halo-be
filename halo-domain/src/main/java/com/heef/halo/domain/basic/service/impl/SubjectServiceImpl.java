package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.dto.authDTO.AuthUserDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.*;
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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        //1. 校验
        if (subjectInfoDTO == null) {
            throw new RuntimeException("题目信息不能为空");
        }
        //2. 对象转换
        SubjectInfo subjectInfo = subjectInfoConvert.toInfoEntity(subjectInfoDTO);
        subjectInfo.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());

        //3. 插入数据到题目info总主表
        int inserted = subjectInfoMapper.insert(subjectInfo);

        //4.使用策略工厂处理不同题型插入
        SubjectTypeHandler subjectTypeHandler = subjectTypeHandlerFactory.getHandler(subjectInfoDTO.getSubjectType());
        subjectInfoDTO.setId(Math.toIntExact(subjectInfo.getId()));// 解决题目id没有插入到对应题目类型表的subjectId的问题
        subjectTypeHandler.addSubject(subjectInfo.getId(), subjectInfoDTO);

        //5. 获取插入数据的id,来同步到从表(关联表)mapping
        Long subjectInfoId = subjectInfo.getId();

        //6. 在mapping处理分类关联
        List<Integer> categoryIds = subjectInfoDTO.getCategoryIds();
        List<Integer> labelIds = subjectInfoDTO.getLabelIds();
        //7. 数据封装
        List<SubjectMapping> mappingList = new LinkedList<>();
        categoryIds.forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(Long.valueOf(subjectInfoId));
                subjectMapping.setCategoryId(Long.valueOf(categoryId));
                subjectMapping.setLabelId(Long.valueOf(labelId));
                subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
                // 添加创建人和更新人信息，避免数据库约束问题
                subjectMapping.setCreatedBy("haloer");
                subjectMapping.setUpdateBy("haloer");
                mappingList.add(subjectMapping);
            });
        });
        // 批量插入关联记录
        if (!CollectionUtils.isEmpty(mappingList)) {
            subjectMappingMapper.insertBatch(mappingList);
        }

        return inserted != 0;
    }

    /**
     * 分页查询题目列表
     *
     * @param subjectInfoDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<SubjectInfoDTO> selectSubjectPage(SubjectInfoDTO subjectInfoDTO, Integer pageNum, Integer pageSize) {
        //计算起始offset
        int offset = (pageNum - 1) * pageSize;

        //对象转换
        SubjectInfo subjectInfo = subjectInfoConvert.toInfoEntity(subjectInfoDTO);

        //查询数据库
        List<SubjectInfo> subjectInfoList = subjectInfoMapper.selectPage(subjectInfo, offset, pageSize);

        Long total = subjectInfoMapper.count(subjectInfo);

        //对象转换返回
        List<SubjectInfoDTO> subjectInfoDTOList = subjectInfoConvert.toInfoDtoList(subjectInfoList);

        //封装返回结果
        return PageResult.<SubjectInfoDTO>builder()
                .pageNo(pageNum)//起始页
                .pageSize(pageSize)//当前页大小
                .total(Math.toIntExact(total))//列表总数
                .result(subjectInfoDTOList)//数据结果列表
                .build();
    }

    /**
     * 分页查询题目列表(面向用户)
     *
     * @param subjectInfoDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<SubjectInfoDTO> selectSubjectPage2(SubjectInfoDTO subjectInfoDTO, Integer pageNum, Integer pageSize) {
        //计算起始offset
        int offset = (pageNum - 1) * pageSize;
        
        //获取分类id和标签id
        Integer categoryId = subjectInfoDTO.getCategoryId();
        Integer labelId = subjectInfoDTO.getLabelId();

        //对象转换
        SubjectInfo subjectInfo = subjectInfoConvert.toInfoEntity(subjectInfoDTO);

        //查询数据库
        List<SubjectInfo> subjectInfoList = subjectInfoMapper.selectPage2(subjectInfo, categoryId, labelId, offset, pageSize);

        Long total = subjectInfoMapper.count2(subjectInfo, categoryId, labelId);

        //对象转换返回
        List<SubjectInfoDTO> subjectInfoDTOList = subjectInfoConvert.toInfoDtoList(subjectInfoList);

        //封装返回结果
        return PageResult.<SubjectInfoDTO>builder()
                .pageNo(pageNum)//起始页
                .pageSize(pageSize)//当前页大小
                .total(Math.toIntExact(total))//列表总数
                .result(subjectInfoDTOList)//数据结果列表
                .build();
    }


    /**
     * 查看题目详情
     *
     * @param subjectInfoDTO
     * @return
     */
    @Override
    public SubjectInfoDTO selectSubjectInfo(SubjectInfoDTO subjectInfoDTO) {
        //对象转换
        SubjectInfo subjectInfo = subjectInfoConvert.toInfoEntity(subjectInfoDTO);
        //查询题目基本信息subjectInfoResult
        SubjectInfo subjectInfoResult = subjectInfoMapper.queryByCondition(subjectInfo);
        //查询具体题目的详情信息
        SubjectTypeHandler subjectTypeHandler = subjectTypeHandlerFactory.getHandler(subjectInfoResult.getSubjectType());
        SubjectOptionDTO subjectOptionDTO = subjectTypeHandler.querySubject(Math.toIntExact(subjectInfoResult.getId()));

        //将查询出来的题目完整信息转换成DTO返回
        SubjectInfoDTO dto = subjectInfoConvert.convertOptionAndInfoToDTO(subjectInfoResult, subjectOptionDTO);


        //根据subjectInfoResult其中的题目id subjectId--到mapping中查询关联的分类标签数据
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setSubjectId(subjectInfoResult.getId());
        subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
        List<SubjectMapping> subjectMappingList = subjectMappingMapper.queryMapping(subjectMapping);

        //遍历subjectMappingList获取里面的标签id
        List<Long> labelList = subjectMappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());

        //通过标签id查询对应的标签信息
        List<SubjectLabel> subjectLabelList = subjectLabelMapper.batchQueryLabel(labelList);
        List<String> labelNameList = subjectLabelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());

        dto.setLabelName(labelNameList);
        return dto;
    }


}