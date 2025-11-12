# 题目模块工厂加策略模式重构设计文档

## 1. 现有架构分析

当前题目模块采用三层架构：
```
Controller(DTO) -> Service(BO) -> Infra(Entity)
```

其中：
- DTO：数据传输对象，用于前后端交互
- BO：业务对象，用于业务逻辑处理
- Entity：实体对象，用于数据库操作

## 2. 重构目标

将架构重构为两层架构，去除BO层：
```
Controller(DTO) -> Service(Entity)
```

优势：
- 简化数据流转层次
- 减少对象转换开销
- 保持系统的扩展性和可维护性

## 3. 重构后的实体类设计

### 3.1 题目信息实体类 (SubjectInfoEntity)

```java
@Data
public class SubjectInfoEntity implements Serializable {
    private static final long serialVersionUID = -98680207564099835L;
    
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 题目名称
     */
    private String subjectName;
    
    /**
     * 题目难度
     */
    private Integer subjectDifficult;
    
    /**
     * 出题人名
     */
    private String settleName;
    
    /**
     * 题目类型 1单选 2多选 3判断 4简答
     */
    private Integer subjectType;
    
    /**
     * 题目分数
     */
    private Integer subjectScore;
    
    /**
     * 题目解析
     */
    private String subjectParse;
    
    /**
     * 题目答案（用于简答题）
     */
    private String subjectAnswer;
    
    /**
     * 创建人
     */
    private String createdBy;
    
    /**
     * 创建时间
     */
    private Date createdTime;
    
    /**
     * 更新人
     */
    private String updatedBy;
    
    /**
     * 更新时间
     */
    private Date updatedTime;
    
    private Integer isDeleted;
    
    /**
     * 分类id列表
     */
    private List<Integer> categoryIds;

    /**
     * 标签id列表
     */
    private List<Integer> labelIds;

    /**
     * 选项列表（用于选择题和判断题）
     */
    private List<SubjectAnswerEntity> optionList;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 标签id
     */
    private Integer labelId;

    /**
     * 标签名字列表
     */
    private List<String> labelName;
}
```

### 3.2 题目答案实体类 (SubjectAnswerEntity)

```java
@Data
public class SubjectAnswerEntity implements Serializable {
    private static final long serialVersionUID = -98680207564099835L;
    
    /**
     * 选项类型编码（1=A, 2=B, 3=C, 4=D）
     */
    private Integer optionType;
    
    /**
     * 选项内容
     */
    private String optionContent;
    
    /**
     * 是否正确答案
     */
    private Integer isCorrect;
    
    /**
     * 题目id（用于关联题目）
     */
    private Integer subjectId;
    
    /**
     * 创建人
     */
    private String createdBy;
    
    /**
     * 创建时间
     */
    private Date createdTime;
    
    /**
     * 更新人
     */
    private String updatedBy;
    
    /**
     * 更新时间
     */
    private Date updatedTime;
    
    private Integer isDeleted;
}
```

### 3.3 题目选项封装类 (SubjectOptionEntity)

```java
@Data
public class SubjectOptionEntity implements Serializable {
    /**
     * 正确答案对应的选项类型（如 "A"）正确
     */
    private String subjectAnswer;
    
    /**
     * 所有选项列表（如 A/B/C/D），每个选项包含内容和是否正确标记
     */
    private List<SubjectAnswerEntity> optionList;

    /**
     * 题目的分类
     */
    private Integer categoryId;

    /**
     * 题目的标签
     */
    private Integer labelId;
}
```

## 4. 策略模式重构设计

### 4.1 题目类型处理接口 (SubjectTypeHandler)

```java
public interface SubjectTypeHandler {
    /**
     * 获取处理的题目类型
     * @return 题目类型枚举
     */
    SubjectInfoTypeEnum getHandlerType();

    /**
     * 添加题目
     * @param subjectInfoEntity 题目实体
     */
    void add(SubjectInfoEntity subjectInfoEntity);

    /**
     * 查询题目选项信息
     * @param subjectId 题目ID
     * @return 题目选项信息
     */
    SubjectOptionEntity query(int subjectId);
}
```

### 4.2 题目类型工厂类 (SubjectTypeHandlerFactory)

```java
@Component
public class SubjectTypeHandlerFactory implements InitializingBean {
    @Autowired
    private List<SubjectTypeHandler> subjectTypeHandlerList;

    /**
     * 定义一个map,来接收策略和枚举,方便操作
     */
    private Map<SubjectInfoTypeEnum, SubjectTypeHandler> handlerMap = new HashMap<>();

    /**
     * 根据题目类型获取对应的处理器
     * @param subjectType 题目类型
     * @return 对应的处理器
     */
    public SubjectTypeHandler getHandler(int subjectType) {
        SubjectInfoTypeEnum subjectInfoTypeEnum = SubjectInfoTypeEnum.getByCode(subjectType);
        return handlerMap.get(subjectInfoTypeEnum);
    }

    /**
     * 在Spring容器完成属性注入后自动调用
     * 初始化handlerMap，建立题目类型枚举到处理器的映射关系
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        for (SubjectTypeHandler subjectTypeHandler : subjectTypeHandlerList) {
            handlerMap.put(subjectTypeHandler.getHandlerType(), subjectTypeHandler);
        }
    }
}
```

### 4.3 各类型题目处理器实现示例

#### 单选题处理器 (RadioTypeHandler)

```java
@Component
public class RadioTypeHandler implements SubjectTypeHandler {
    @Autowired
    private SubjectRadioService subjectRadioService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    @Override
    public void add(SubjectInfoEntity subjectInfoEntity) {
        // 单项选择题的添加
        List<SubjectRadioEntity> subjectRadioList = new LinkedList<>();
        
        subjectInfoEntity.getOptionList().forEach(option -> {
            SubjectRadioEntity subjectRadio = RadioSubjectConverter.INSTANCE.convertEntityToEntity(option);
            subjectRadio.setSubjectId(subjectInfoEntity.getId());
            subjectRadio.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
            subjectRadioList.add(subjectRadio);
        });
        
        subjectRadioService.InsertBatch(subjectRadioList);
    }

    @Override
    public SubjectOptionEntity query(int subjectId) {
        SubjectRadioEntity subjectRadio = new SubjectRadioEntity();
        subjectRadio.setSubjectId(subjectId);
        List<SubjectRadioEntity> subjectRadioList = subjectRadioService.queryByCondition(subjectRadio);

        List<SubjectAnswerEntity> subjectAnswerEntityList = RadioSubjectConverter.INSTANCE.convertEntityListToEntityList(subjectRadioList);
        SubjectOptionEntity subjectOptionEntity = new SubjectOptionEntity();
        subjectOptionEntity.setOptionList(subjectAnswerEntityList);
        return subjectOptionEntity;
    }
}
```

#### 简答题处理器 (BriefTypeHandler)

```java
@Component
public class BriefTypeHandler implements SubjectTypeHandler {
    @Autowired
    private SubjectBriefService subjectBriefService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }
    
    @Override
    public void add(SubjectInfoEntity subjectInfoEntity) {
        SubjectBriefEntity subjectBrief = BriefSubjectConverter.INSTANCE.convertEntityToEntity(subjectInfoEntity);
        subjectBrief.setSubjectId(subjectInfoEntity.getId());
        subjectBrief.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
        subjectBriefService.insert(subjectBrief);
    }

    @Override
    public SubjectOptionEntity query(int subjectId) {
        SubjectBriefEntity subjectBrief = new SubjectBriefEntity();
        subjectBrief.setSubjectId(subjectId);
        SubjectBriefEntity result = subjectBriefService.queryByCondition(subjectBrief);

        SubjectOptionEntity subjectOptionEntity = new SubjectOptionEntity();
        subjectOptionEntity.setSubjectAnswer(result.getSubjectAnswer());
        return subjectOptionEntity;
    }
}
```

## 5. 转换器设计

### 5.1 DTO与Entity转换器 (SubjectInfoConverter)

```java
@Mapper
public interface SubjectInfoConverter {
    SubjectInfoConverter INSTANCE = Mappers.getMapper(SubjectInfoConverter.class);

    // DTO转Entity
    SubjectInfoEntity convertDtoToEntity(SubjectInfoDTO subjectInfoDTO);

    // Entity转DTO
    SubjectInfoDTO convertEntityToDto(SubjectInfoEntity subjectInfoEntity);

    // Entity列表转DTO列表
    List<SubjectInfoDTO> convertEntityListToDtoList(List<SubjectInfoEntity> subjectInfoEntityList);
    
    // 选项信息Entity与题目信息Entity合并
    SubjectInfoDTO mergeOptionAndInfoToDto(SubjectOptionEntity optionEntity, SubjectInfoEntity infoEntity);
}
```

## 6. 服务层重构

### 6.1 题目信息服务接口 (SubjectInfoDomainService)

```java
public interface SubjectInfoDomainService {
    /**
     * 新增题目
     * @param subjectInfoEntity 题目实体
     */
    void add(SubjectInfoEntity subjectInfoEntity);

    /**
     * 分页查询题目
     * @param subjectInfoEntity 查询条件
     * @return 分页结果
     */
    PageResult<SubjectInfoEntity> getSubjectPage(SubjectInfoEntity subjectInfoEntity);

    /**
     * 查询题目详情
     * @param subjectInfoEntity 查询条件
     * @return 题目详情
     */
    SubjectInfoEntity querySubjectInfo(SubjectInfoEntity subjectInfoEntity);
}
```

### 6.2 题目信息服务实现类 (SubjectInfoDomainServiceImpl)

```java
@Slf4j
@Service("SubjectInfoDomainService")
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {
    @Autowired
    private SubjectInfoService subjectInfoService;

    @Autowired
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    @Autowired
    private SubjectMappingService subjectMappingService;

    @Autowired
    private SubjectLabelService subjectLabelService;

    /**
     * 新增题目
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SubjectInfoEntity subjectInfoEntity) {
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.add.entity: {}", JSON.toJSONString(subjectInfoEntity));
        }
        
        // 插入题目基本信息
        subjectInfoService.insert(subjectInfoEntity);
        
        // 使用工厂+策略模式处理不同类型题目的选项信息
        SubjectTypeHandler subjectTypeHandler = subjectTypeHandlerFactory.getHandler(subjectInfoEntity.getSubjectType());
        subjectTypeHandler.add(subjectInfoEntity);
        
        // 处理题目与分类、标签的关联关系
        List<Integer> categoryIds = subjectInfoEntity.getCategoryIds();
        List<Integer> labelIds = subjectInfoEntity.getLabelIds();

        List<SubjectMappingEntity> mappingList = new LinkedList<>();
        categoryIds.forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                SubjectMappingEntity subjectMapping = new SubjectMappingEntity();
                subjectMapping.setSubjectId(Long.valueOf(subjectInfoEntity.getId()));
                subjectMapping.setCategoryId(Long.valueOf(categoryId));
                subjectMapping.setLabelId(Long.valueOf(labelId));
                subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
                mappingList.add(subjectMapping);
            });
        });
        
        // 批量插入题目关系数据
        subjectMappingService.InsertBatch(mappingList);
    }

    /**
     * 查询题目详情
     */
    @Override
    public SubjectInfoEntity querySubjectInfo(SubjectInfoEntity subjectInfoEntity) {
        // 查询题目基本信息
        SubjectInfoEntity infoEntity = subjectInfoService.queryById(subjectInfoEntity.getId());
        
        // 使用工厂获取对应题型的处理器查询选项信息
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(infoEntity.getSubjectType());
        SubjectOptionEntity optionEntity = handler.query(infoEntity.getId().intValue());
        
        // 合并题目基本信息和选项信息
        SubjectInfoEntity resultEntity = SubjectInfoConverter.INSTANCE.mergeOptionAndInfoToEntity(optionEntity, infoEntity);

        // 查询题目关联的标签
        SubjectMappingEntity subjectMapping = new SubjectMappingEntity();
        subjectMapping.setSubjectId(Long.valueOf(infoEntity.getId()));
        subjectMapping.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
        List<SubjectMappingEntity> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        List<Long> labelIdList = mappingList.stream().map(SubjectMappingEntity::getLabelId).collect(Collectors.toList());

        // 根据标签id进行批量查询
        List<SubjectLabelEntity> labelList = subjectLabelService.batchQueryById(labelIdList);

        // 组装所有数据返回
        List<String> labelNameList = labelList.stream().map(SubjectLabelEntity::getLabelName).collect(Collectors.toList());
        resultEntity.setLabelName(labelNameList);

        return resultEntity;
    }
    
    // ... 其他方法实现
}
```

## 7. 总结

重构为两层架构的关键点：

1. **简化数据传输**：去掉了BO层，直接使用Entity承载业务数据
2. **保持策略模式**：题目类型的处理仍然使用工厂+策略模式，保证扩展性
3. **实体类增强**：Entity类增加了原本在BO层的业务属性，如选项列表等
4. **转换器优化**：简化了DTO与Entity之间的转换逻辑
5. **服务层调整**：服务层直接操作Entity，减少了一层转换

这种设计保留了原有系统的扩展性和可维护性，同时减少了层级，使代码更加简洁。每种题型的处理逻辑仍然相互独立，新增题型时只需实现SubjectTypeHandler接口即可。