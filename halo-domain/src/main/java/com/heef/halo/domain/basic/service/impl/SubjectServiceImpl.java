package com.heef.halo.domain.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.Preconditions;
import com.heef.halo.domain.basic.dto.staticDTO.DailyStatisticsDTO;
import com.heef.halo.domain.basic.dto.staticDTO.RankDTO;
import com.heef.halo.domain.basic.dto.staticDTO.RankDetailDTO;
import com.heef.halo.domain.basic.dto.subjectDTO.*;
import com.heef.halo.domain.basic.entity.*;
import com.heef.halo.domain.basic.handler.subject.SubjectTypeHandler;
import com.heef.halo.domain.basic.handler.subject.SubjectTypeHandlerFactory;
import com.heef.halo.domain.basic.mapper.*;
import com.heef.halo.domain.basic.service.SubjectService;
import com.heef.halo.domain.convert.SubjectCategoryConvert;
import com.heef.halo.domain.convert.SubjectInfoConvert;
import com.heef.halo.domain.convert.SubjectLabelConvert;
import com.heef.halo.domain.convert.SubjectRecordConvert;
import com.heef.halo.enums.IsDeleteFlagEnum;
import com.heef.halo.result.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
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
    private SubjectRecordMapper subjectRecordMapper;

    @Autowired
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    @Autowired
    private SubjectCategoryConvert subjectCategoryConvert;

    @Autowired
    private SubjectLabelConvert subjectLabelConvert;

    @Autowired
    private SubjectInfoConvert subjectInfoConvert;
    
    @Autowired
    private SubjectRecordConvert subjectRecordConvert;

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    // 排行榜在Redis中的key前缀
    private static final String RANKING_PREFIX = "ranking:";
    
    // 排行榜过期时间（秒）
    private static final long RANKING_EXPIRE_TIME = 2 * 60; // 2分钟

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
        //传递需要修改的数据
        convertEntity.setId(id);

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
     * 查询分类下大类(根据父分类id查询子分类)
     *
     * @param subjectCategoryDTO
     * @return
     */
    @Override
    public List<SubjectCategoryDTO> selectCategoryByPrimary(SubjectCategoryDTO subjectCategoryDTO) {
        Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "父级id不能为空");

        //对象转换
        SubjectCategory subjectCategory = subjectCategoryConvert.toEntity(subjectCategoryDTO);
        subjectCategory.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());

        //查询数据库
        List<SubjectCategory> subjectCategoryList = subjectCategoryMapper.selectCategoryByParentId(subjectCategory);

        //查询出来的list转换返回
        List<SubjectCategoryDTO> subjectCategoryConvertDtoList = subjectCategoryConvert.toDtoList(subjectCategoryList);

        //顺便把该分类下有多少题目都查询出来--根据查询出来的分类id到关联表查询题目数量
        subjectCategoryConvertDtoList.forEach(dto -> {
            Integer subjectCount = subjectMappingMapper.querySubjectCount(dto.getId());
            dto.setSubjectCount(subjectCount);
        });

        return subjectCategoryConvertDtoList;
    }

    /**
     * 查询分类下大类及其标签
     *
     *
     * 1:查询分类列表 首先根据传入的父分类ID查询所有子分类：
     * 2:查询标签信息 对每个分类查询其关联的标签信息
     * 3:查询标签详细信息的具体实现
     *   3.1查询中间表：根据分类ID查询 SubjectMapping 表获取关联的标签ID列表
     *   3.2提取标签ID：从中间表结果中提取所有标签ID
     *   3.3批量查询标签：根据标签ID列表批量查询标签详细信息
     * 4:关联分类和标签 最后将查询到的标签信息关联到对应的分类上
     *
     * @param subjectCategoryDTO
     * @return
     */
    @Override
    public List<SubjectCategoryDTO> selectCategoryAndLabel(SubjectCategoryDTO subjectCategoryDTO) {
        // 校验
        Preconditions.checkNotNull(subjectCategoryDTO.getId(), "分类id不能为空");

        // 根据当前分类ID查询所有子分类
        subjectCategoryDTO.setParentId(subjectCategoryDTO.getId());
        subjectCategoryDTO.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
        // 对象转换
        SubjectCategory subjectCategory = subjectCategoryConvert.toEntity(subjectCategoryDTO);
        //对象转换时保留了 id 字段，导致 SQL 查询条件变成了既要 id=1 又要 parent_id=1，这样的记录通常不存在。
        subjectCategory.setId(null);

        // 查询出所有子分类
        List<SubjectCategory> subjectCategoryList = subjectCategoryMapper.selectCategoryByParentId(subjectCategory);
        log.info("查询子分类数据列表:{}", subjectCategoryList);

        // 如果查询结果为空，直接返回空列表
        if (CollectionUtils.isEmpty(subjectCategoryList)) {
            return new ArrayList<>();
        }

        // 提取所有分类ID
        List<Long> categoryIds = subjectCategoryList.stream()
                .map(SubjectCategory::getId)
                .collect(Collectors.toList());

        // 根据分类ID列表查询关联表数据
        List<SubjectMapping> mappingList = subjectMappingMapper.queryMappingByCategoryIdList(categoryIds);

        // 按分类ID分组标签ID
        Map<Long, List<Long>> categoryIdToLabelIdsMap = mappingList.stream()
                .collect(Collectors.groupingBy(
                        SubjectMapping::getCategoryId,
                        Collectors.mapping(SubjectMapping::getLabelId, Collectors.toList())
                ));

        // 查询所有涉及的标签信息
        List<Long> labelIds = mappingList.stream()
                .map(SubjectMapping::getLabelId)
                .distinct()
                .collect(Collectors.toList());

        List<SubjectLabel> labelList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(labelIds)) {
            labelList = subjectLabelMapper.queryLabelByLabelIdList(labelIds);
        }

        // 按标签ID建立映射
        Map<Long, SubjectLabel> labelIdToLabelMap = labelList.stream()
                .collect(Collectors.toMap(SubjectLabel::getId, label -> label));

        // 将标签信息关联到分类上
        List<SubjectCategoryDTO> result = subjectCategoryConvert.toDtoList(subjectCategoryList);
        result.forEach(categoryDTO -> {
            List<Long> labelIdList = categoryIdToLabelIdsMap.getOrDefault(categoryDTO.getId(), new ArrayList<>());
            // 去除重复的标签ID
            List<Long> distinctLabelIdList = labelIdList.stream().distinct().collect(Collectors.toList());
            List<SubjectLabelDTO> labelDTOList = distinctLabelIdList.stream()
                    .map(labelIdToLabelMap::get)
                    .filter(Objects::nonNull)
                    .map(subjectLabelConvert::toLabelDto)
                    .collect(Collectors.toList());
            categoryDTO.setLabelDTOList(labelDTOList);
        });

        return result;
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

        //4.使用策略工厂处理不同题型插入(传枚举题型--工厂根据类型造数据)
        SubjectTypeHandler subjectTypeHandler = subjectTypeHandlerFactory.getHandler(subjectInfoDTO.getSubjectType());
        subjectInfoDTO.setId(Math.toIntExact(subjectInfo.getId())); //将主表id同步到具体类型表
        subjectTypeHandler.addSubject(subjectInfo.getId(), subjectInfoDTO);

        //5. 获取插入数据的id,来同步到从表(关联表)mapping
        Long subjectInfoId = subjectInfo.getId();

        //6. 在mapping处理分类关联
        List<Integer> categoryIds = subjectInfoDTO.getCategoryIds();
        List<Integer> labelIds = subjectInfoDTO.getLabelIds();
        //7. 数据封装(聚合添加--一条数据对应一组分类/标签)
        List<SubjectMapping> mappingList = new LinkedList<>();
        categoryIds.forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(Long.valueOf(subjectInfoId));
                subjectMapping.setCategoryId(Long.valueOf(categoryId));
                subjectMapping.setLabelId(Long.valueOf(labelId));
                subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
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
        List<Integer> labelIds = subjectInfoDTO.getLabelIds_query();

        //对象转换
        SubjectInfo subjectInfo = subjectInfoConvert.toInfoEntity(subjectInfoDTO);

        //查询数据库
        List<SubjectInfo> subjectInfoList;
        Long total;
        
        // 如果传来了多个标签id，使用新方法
        if (!CollectionUtils.isEmpty(labelIds)) {
            subjectInfoList = subjectInfoMapper.selectPage3(subjectInfo, categoryId, labelIds, offset, pageSize);
            total = subjectInfoMapper.count3(subjectInfo, categoryId, labelIds);
        } else {
            // 否则使用原来的方法
            subjectInfoList = subjectInfoMapper.selectPage2(subjectInfo, categoryId, labelId, offset, pageSize);
            total = subjectInfoMapper.count2(subjectInfo, categoryId, labelId);
        }

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
        List<Integer> labelIdList = labelList.stream().map(Long::intValue).collect(Collectors.toList());

        //通过标签id查询对应的标签信息
        List<SubjectLabel> subjectLabelList = subjectLabelMapper.queryLabelByLabelIdList(labelList);
        List<String> labelNameList = subjectLabelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());

        //获取分类ID列表
        List<Long> categoryList = subjectMappingList.stream().map(SubjectMapping::getCategoryId).collect(Collectors.toList());
        List<Integer> categoryIdList = categoryList.stream().map(Long::intValue).collect(Collectors.toList());

        dto.setLabelName(labelNameList);
        dto.setLabelIds(labelIdList);
        dto.setCategoryIds(categoryIdList);
        return dto;
    }

    /**
     * 编辑题目
     * @param subjectInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSubjectInfo(SubjectInfoDTO subjectInfoDTO) {
        // 1. 验证题目是否存在
        SubjectInfo subjectInfoEntity = subjectInfoMapper.selectById(subjectInfoDTO.getId());
        if(subjectInfoEntity == null){
            return false;
        }

        // 2. 更新题目基本信息
        SubjectInfo subjectInfo = subjectInfoConvert.toInfoEntity(subjectInfoDTO);
        int updated = subjectInfoMapper.updateById(subjectInfo); // 使用updateById
        if(updated == 0) {
            return false;
        }

        // 3. 处理关联关系 - 先删除后插入
        // 查询现有的mapping关联数据
        SubjectMapping queryMapping = new SubjectMapping();
        queryMapping.setSubjectId(subjectInfoEntity.getId());
        List<SubjectMapping> subjectMappingList = subjectMappingMapper.queryMapping(queryMapping);

        // 根据关联id删除关联表信息
        if (subjectMappingList != null && !subjectMappingList.isEmpty()) {
            List<Long> mappingIds = subjectMappingList.stream()
                    .map(SubjectMapping::getId)
                    .collect(Collectors.toList());
            subjectMappingMapper.deleteBatchIds(mappingIds); // 使用deleteBatchIds
        }

        // 4. 生成新的关联信息
        if (subjectInfoDTO.getCategoryIds() != null && !subjectInfoDTO.getCategoryIds().isEmpty()
                && subjectInfoDTO.getLabelIds() != null && !subjectInfoDTO.getLabelIds().isEmpty()) {

            for (Integer categoryId : subjectInfoDTO.getCategoryIds()) {
                for (Integer labelId : subjectInfoDTO.getLabelIds()) {
                    SubjectMapping mapping = new SubjectMapping();
                    mapping.setSubjectId(subjectInfoEntity.getId());
                    mapping.setCategoryId(categoryId.longValue());
                    mapping.setLabelId(labelId.longValue());
                    mapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
                    subjectMappingMapper.insert(mapping);
                }
            }
        }
        return true;
    }

    /**
     * 删除题目
     * @param subjectInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSubject(SubjectInfoDTO subjectInfoDTO) {
        SubjectInfo subjectInfo = subjectInfoMapper.selectById(subjectInfoDTO.getId());
        if(subjectInfo==null){
            return false;
        }
        //删除题目信息(info表)
        SubjectInfo subjectInfoEntity = subjectInfoConvert.toInfoEntity(subjectInfoDTO);
        subjectInfoMapper.deleteById(subjectInfoEntity.getId());

        //根据subject_id删除关联表信息
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setSubjectId(subjectInfoEntity.getId());

        List<SubjectMapping> subjectMappingList = subjectMappingMapper.queryMapping(subjectMapping);
        List<Long> mappingIds = subjectMappingList.stream().map(SubjectMapping::getId).collect(Collectors.toList());

        int deleted = subjectMappingMapper.deleteBatch(mappingIds);

        return deleted!=0;
    }


    /**
     * 保存刷题记录
     * @param subjectRecordDTO
     * @return
     */
    @Override
    public Boolean SaveRecord(SubjectRecordDTO subjectRecordDTO) {
        // 对象转换
        SubjectRecord subjectRecord = subjectRecordConvert.toRecordEntity(subjectRecordDTO);
        
        // 设置创建时间和更新时间
        Date now = new Date();
        if (subjectRecord.getCreateTime() == null) {
            subjectRecord.setCreateTime(now);
        }
        subjectRecord.setUpdateTime(now);
        
        // 插入数据
        int result = subjectRecordMapper.insert(subjectRecord);

        return result > 0;
    }
    
    /**
     * 获取用户答题记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<SubjectRecordDTO> getRecordByUser(Long userId) {
        List<SubjectRecord> recordList = subjectRecordMapper.getRecordByUser(userId);
        List<SubjectRecordDTO> recordDTOList = subjectRecordConvert.toRecordDtoList(recordList);
        
        // 为每个记录添加题目信息
        for (SubjectRecordDTO recordDTO : recordDTOList) {
            // 查询题目信息
            SubjectInfo subjectInfo = subjectInfoMapper.selectById(recordDTO.getSubjectId());
            if (subjectInfo != null) {
                recordDTO.setSubjectName(subjectInfo.getSubjectName());
                
                // 查询题目分类信息
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(recordDTO.getSubjectId());
                subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
                List<SubjectMapping> mappingList = subjectMappingMapper.queryMapping(subjectMapping);
                
                if (!CollectionUtils.isEmpty(mappingList)) {
                    // 获取第一个分类ID
                    Long categoryId = mappingList.get(0).getCategoryId();
                    recordDTO.setCategoryId(categoryId);
                    
                    // 查询分类名称
                    SubjectCategory category = subjectCategoryMapper.selectById(categoryId);
                    if (category != null) {
                        recordDTO.setCategoryName(category.getCategoryName());
                    }
                }
            }
        }
        
        return recordDTOList;
    }

    /**
     * 获取题目答题记录
     *
     * @param subjectId
     * @return
     */
    @Override
    public List<SubjectRecordDTO> getRecordBySubject(Long subjectId) {
        List<SubjectRecord> recordList = subjectRecordMapper.getRecordBySubject(subjectId);
        List<SubjectRecordDTO> recordDTOList = subjectRecordConvert.toRecordDtoList(recordList);
        
        // 为每个记录添加题目信息
        for (SubjectRecordDTO recordDTO : recordDTOList) {
            // 查询题目信息
            SubjectInfo subjectInfo = subjectInfoMapper.selectById(recordDTO.getSubjectId());
            if (subjectInfo != null) {
                recordDTO.setSubjectName(subjectInfo.getSubjectName());
                
                // 查询题目分类信息
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(recordDTO.getSubjectId());
                subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
                List<SubjectMapping> mappingList = subjectMappingMapper.queryMapping(subjectMapping);
                
                if (!CollectionUtils.isEmpty(mappingList)) {
                    // 获取第一个分类ID
                    Long categoryId = mappingList.get(0).getCategoryId();
                    recordDTO.setCategoryId(categoryId);
                    
                    // 查询分类名称
                    SubjectCategory category = subjectCategoryMapper.selectById(categoryId);
                    if (category != null) {
                        recordDTO.setCategoryName(category.getCategoryName());
                    }
                }
            }
        }
        
        return recordDTOList;
    }
    
    /**
     * 获取用户当日刷题统计
     * 
     * @param userId 用户ID
     * @param date 指定日期
     * @return 每日统计信息
     */
    @Override
    public DailyStatisticsDTO getDailyStatistics(Long userId, Date date) {
        // 计算当天的开始和结束时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startTime = calendar.getTime();
        
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endTime = calendar.getTime();
        
        // 获取当天的刷题记录
        List<SubjectRecord> dailyRecords = subjectRecordMapper.getDailyRecords(userId, startTime, endTime);
        
        // 统计数据
        DailyStatisticsDTO dailyStats = new DailyStatisticsDTO();
        dailyStats.setProblemCount(dailyRecords.size());
        
        int totalTime = 0;
        int correctCount = 0;
        int totalScore = 0;
        
        for (SubjectRecord record : dailyRecords) {
            totalTime += record.getTimeCost() != null ? record.getTimeCost() : 0;
            if (record.getIsCorrect() != null && record.getIsCorrect() == 1) {
                correctCount++;
            }
            totalScore += record.getScore() != null ? record.getScore() : 0;
        }
        
        dailyStats.setTotalTime(totalTime);
        dailyStats.setTotalScore(totalScore);
        
        // 计算正确率
        if (dailyRecords.size() > 0) {
            double accuracy = (double) correctCount / dailyRecords.size() * 100;
            dailyStats.setAccuracy(Math.round(accuracy * 100.0) / 100.0); // 保留两位小数
        }
        
        return dailyStats;
    }
    
    /**
     * 获取用户已解决的题目数量
     * 
     * @param userId 用户ID
     * @return 已解决的题目数量
     */
    @Override
    public int getSolvedProblemsCount(Long userId) {
        return subjectRecordMapper.countSolvedProblems(userId);
    }
    
    /**
     * 获取用户尝试的题目数量
     * 
     * @param userId 用户ID
     * @return 尝试的题目数量
     */
    @Override
    public int getAttemptedProblemsCount(Long userId) {
        return subjectRecordMapper.countAttemptedProblems(userId);
    }
    
    /**
     * 获取排行榜数据列表
     * 
     * @param timeRange 时间范围 (today, week, month)
     * @param rankingType 排行类型 (problemCount, score, correctCount)
     * @return 排行榜数据列表
     */
    @Override
    public List<RankDTO> getRankList(String timeRange, String rankingType) {
        // 构造Redis key
        String key = RANKING_PREFIX + timeRange + ":" + rankingType;
        
        // 从Redis获取排行榜数据
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> tuples = zSetOps.reverseRangeWithScores(key, 0, 99); // 获取前100名
        
        List<RankDTO> rankList = new ArrayList<>();
        if (tuples != null && !tuples.isEmpty()) {
            int rank = 1;
            for (ZSetOperations.TypedTuple<Object> tuple : tuples) {
                RankDTO rankDTO = (RankDTO) tuple.getValue();
                rankDTO.setValue(tuple.getScore().intValue());
                
                // 设置趋势（简化处理，实际项目中可以根据历史数据计算）
                rankDTO.setTrend("--");
                
                rankList.add(rankDTO);
                rank++;
            }
        } else {
            // 如果Redis中没有数据，则从数据库查询并缓存到Redis
            rankList = calculateAndCacheRanking(timeRange, rankingType);
        }
        
        return rankList;
    }
    
    /**
     * 获取排行榜详情
     * 
     * @param timeRange 时间范围 (today, week, month)
     * @param rankingType 排行类型 (problemCount, score, correctCount)
     * @param userId 当前用户ID
     * @return 排行榜详情数据
     */
    @Override
    public RankDetailDTO getRankDetail(String timeRange, String rankingType, Long userId) {
        // 获取排行榜列表
        List<RankDTO> rankings = getRankList(timeRange, rankingType);
        
        // 查找当前用户在排行榜中的位置
        RankDTO currentUserRank = null;
        int currentUserRanking = 0;
        for (int i = 0; i < rankings.size(); i++) {
            RankDTO rankDTO = rankings.get(i);
            if (rankDTO.getId().equals(userId)) {
                currentUserRank = rankDTO;
                currentUserRanking = i + 1; // 排名从1开始
                break;
            }
        }
        
        // 构造返回结果
        RankDetailDTO rankDetailDTO = new RankDetailDTO();
        rankDetailDTO.setRankings(rankings);
        if (currentUserRank != null) {
            rankDetailDTO.setCurrentUserRank(currentUserRanking);
            rankDetailDTO.setCurrentUserValue(currentUserRank.getValue());
        } else {
            // 如果用户不在排行榜中，设置默认值
            rankDetailDTO.setCurrentUserRank(0);
            rankDetailDTO.setCurrentUserValue(0);
        }
        
        return rankDetailDTO;
    }
    
    /**
     * 计算并缓存排行榜数据
     * 
     * @param timeRange 时间范围
     * @param rankingType 排行类型
     * @return 排行榜数据列表
     */
    private List<RankDTO> calculateAndCacheRanking(String timeRange, String rankingType) {
        List<RankDTO> rankList = new ArrayList<>();
        
        // 获取所有用户
        List<AuthUser> allUsers = authUserMapper.selectList(new AuthUser());
        
        // 构造Redis key
        String key = RANKING_PREFIX + timeRange + ":" + rankingType;
        
        // 计算每个用户的排行数据
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        
        for (AuthUser user : allUsers) {
            // 计算用户在指定时间范围内的统计数据
            int value = calculateUserRankValue(user.getId(), timeRange, rankingType);
            
            // 创建RankDTO对象
            RankDTO rankDTO = new RankDTO();
            rankDTO.setId(user.getId());
            rankDTO.setUserName(user.getNickName() != null ? user.getNickName() : user.getUserName());
            rankDTO.setAvatar(user.getAvatar());
            rankDTO.setTrend("--"); // 简化处理，实际项目中可以根据历史数据计算
            
            // 添加到Redis有序集合中
            zSetOps.add(key, rankDTO, value);
            
            // 同时添加到返回列表中
            rankDTO.setValue(value);
            rankList.add(rankDTO);
        }
        
        // 设置过期时间
        redisTemplate.expire(key, RANKING_EXPIRE_TIME, TimeUnit.SECONDS);
        
        // 按分数降序排序
        rankList.sort((r1, r2) -> r2.getValue().compareTo(r1.getValue()));
        
        return rankList;
    }
    
    /**
     * 计算用户在指定时间范围内的排行值
     * 
     * @param userId 用户ID
     * @param timeRange 时间范围
     * @param rankingType 排行类型
     * @return 排行值
     */
    private int calculateUserRankValue(Long userId, String timeRange, String rankingType) {
        //根据时间范围表示来计算获取是每天凌晨00:00还是每周一凌晨00:00还是每月1号00:00
        Date startTime = getStartTime(timeRange);
        Date endTime = new Date(); // 当前时间
        
        // 添加日志输出，明确显示参数值
        if (log.isDebugEnabled()) {
            log.debug("查询用户排行榜数据: userId={}, timeRange={}, rankingType={}, startTime={}, endTime={}", 
                      userId, timeRange, rankingType, startTime, endTime);
        }
        
        // 获取用户在指定时间范围内的答题记录(使用新的排行榜专用方法)
        List<SubjectRecord> records = subjectRecordMapper.getRankRecords(userId, startTime, endTime);
        
        switch (rankingType) {
            case "problemCount": // 刷题数(用户的刷题记录数)
                return records.size();
            case "score": // 得分(用户刷题记录的分数值)
                return records.stream().mapToInt(record -> record.getScore() != null ? record.getScore() : 0).sum();
            case "correctCount": // 正确数 (用户的回答正确数)
                return (int) records.stream().filter(record -> record.getIsCorrect() != null && record.getIsCorrect() == 1).count();
            default:
                return 0;
        }
    }
    
    /**
     * 根据时间范围获取开始时间
     * 
     * @param timeRange 时间范围
     * @return 开始时间
     */
    private Date getStartTime(String timeRange) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        
        switch (timeRange) {
            case "today": // 今天
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            case "week": // 本周
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            case "month": // 本月
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            default:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
        }
        
        return calendar.getTime();
    }
}