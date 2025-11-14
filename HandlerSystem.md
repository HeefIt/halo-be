# 题目模块工厂加策略模式设计文档

## 1. 系统架构说明

当前题目模块采用标准的分层架构：
```
Controller(DTO) -> Service -> Mapper -> Entity
```

其中：
- Controller层：负责处理HTTP请求，参数校验和响应封装
- Service层：负责业务逻辑处理
- Mapper层：负责数据访问
- Entity：实体对象，与数据库表结构一一对应
- DTO：数据传输对象，用于前后端交互

## 2. 题目实体类设计

### 2.1 题目信息实体类 (SubjectInfo)

```java
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubjectInfo implements Serializable {
    private static final long serialVersionUID = -71401209593239253L;
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
     * 题目答案
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
     * 修改人
     */
    private String updateBy;
    
    /**
     * 修改时间
     */
    private Date updateTime;
    
    private Integer isDeleted;
}
```

### 2.2 题目答案实体类 (SubjectAnswer)

```java
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubjectAnswer implements Serializable {
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
    private String updateBy;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    private Integer isDeleted;
}
```

## 3. 策略模式设计

### 3.1 题目类型处理接口 (SubjectTypeHandler)

```java
public interface SubjectTypeHandler {
    /**
     * 获取处理的题目类型
     * @return 题目类型枚举
     */
    SubjectInfoTypeEnum getHandlerType();

    /**
     * 添加题目
     * @param subjectId 题目ID
     * @param subjectInfoDTO 题目信息
     */
    void addSubject(Long subjectId, SubjectInfoDTO subjectInfoDTO);

    /**
     * 查询题目选项信息
     * @param subjectId 题目ID
     * @return 题目选项信息
     */
    SubjectOptionDTO querySubject(int subjectId);
}
```

### 3.2 题目类型工厂类 (SubjectTypeHandlerFactory)

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

### 3.3 各类型题目处理器实现示例

#### 单选题处理器 (RadioSubjectTypeHandler)

```java
@Component
public class RadioSubjectTypeHandler implements SubjectTypeHandler {
    @Autowired
    private SubjectRadioService subjectRadioService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    @Override
    public void addSubject(Long subjectId, SubjectInfoDTO subjectInfoDTO) {
        // 单项选择题的添加
        List<SubjectRadioDTO> subjectRadioList = new LinkedList<>();
        
        subjectInfoDTO.getOptionList().forEach(option -> {
            SubjectRadioDTO subjectRadioDTO = new SubjectRadioDTO();
            subjectRadioDTO.setSubjectId(subjectId);
            subjectRadioDTO.setOptionType(option.getOptionType());
            subjectRadioDTO.setOptionContent(option.getOptionContent());
            subjectRadioDTO.setIsCorrect(option.getIsCorrect());
            subjectRadioDTO.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
            subjectRadioList.add(subjectRadioDTO);
        });
        
        subjectRadioService.InsertBatch(subjectRadioList);
    }

    @Override
    public SubjectOptionDTO querySubject(int subjectId) {
        SubjectRadioDTO subjectRadioDTO = new SubjectRadioDTO();
        subjectRadioDTO.setSubjectId((long) subjectId);
        List<SubjectRadioDTO> subjectRadioList = subjectRadioService.queryByCondition(subjectRadioDTO);

        SubjectOptionDTO subjectOptionDTO = new SubjectOptionDTO();
        subjectOptionDTO.setOptionList(subjectRadioList);
        return subjectOptionDTO;
    }
}
```

#### 简答题处理器 (BriefSubjectTypeHandler)

```java
@Component
public class BriefSubjectTypeHandler implements SubjectTypeHandler {
    @Autowired
    private SubjectBriefService subjectBriefService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }
    
    @Override
    public void addSubject(Long subjectId, SubjectInfoDTO subjectInfoDTO) {
        SubjectBriefDTO subjectBriefDTO = new SubjectBriefDTO();
        subjectBriefDTO.setSubjectId(subjectId);
        subjectBriefDTO.setSubjectAnswer(subjectInfoDTO.getSubjectAnswer());
        subjectBriefDTO.setIsDeleted(IsDeleteFlagEnum.UN_DELETE.getCode());
        subjectBriefService.insert(subjectBriefDTO);
    }

    @Override
    public SubjectOptionDTO querySubject(int subjectId) {
        SubjectBriefDTO subjectBriefDTO = new SubjectBriefDTO();
        subjectBriefDTO.setSubjectId((long) subjectId);
        SubjectBriefDTO result = subjectBriefService.queryByCondition(subjectBriefDTO);

        SubjectOptionDTO subjectOptionDTO = new SubjectOptionDTO();
        subjectOptionDTO.setSubjectAnswer(result.getSubjectAnswer());
        return subjectOptionDTO;
    }
}
```

## 4. 转换器设计

### 4.1 DTO与Entity转换器 (SubjectInfoConvert)

```java
@Mapper
public interface SubjectInfoConvert {
    SubjectInfoConvert INSTANCE = Mappers.getMapper(SubjectInfoConvert.class);

    // DTO转Entity
    SubjectInfo toInfoEntity(SubjectInfoDTO subjectInfoDTO);

    // Entity转DTO
    SubjectInfoDTO convertInfoToDTO(SubjectInfo subjectInfo);
    
    // Entity转DTO（带选项信息）
    SubjectInfoDTO convertOptionAndInfoToDTO(SubjectInfo subjectInfo, SubjectOptionDTO subjectOptionDTO);

    // Entity列表转DTO列表
    List<SubjectInfoDTO> toInfoDtoList(List<SubjectInfo> subjectInfoList);
}
```

## 5. 题目管理接口文档

### 5.1 题目分类管理接口

#### 新增题目分类
- **接口地址**: `POST /api/subject/category/add`
- **请求参数**:
```
{
  "categoryName": "Java基础",
  "categoryType": 1,
  "parentId": 0
}
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": true
}
```

#### 分页查询分类列表
- **接口地址**: `GET /api/subject/category/selectPage`
- **请求参数**:
```
categoryName: Java     // 分类名称（可选）
pageNum: 1             // 页码（默认1）
pageSize: 10           // 每页条数（默认10）
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 100,
    "pages": 10,
    "result": [
      {
        "id": 1,
        "categoryName": "Java基础",
        "categoryType": 1,
        "parentId": 0
      }
    ]
  }
}
```

#### 修改分类
- **接口地址**: `PUT /api/subject/category/update/{id}`
- **请求参数**:
```
{
  "categoryName": "Java进阶",
  "categoryType": 1,
  "parentId": 0
}
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": true
}
```

#### 删除分类
- **接口地址**: `DELETE /api/subject/category/delete/{id}`
- **请求参数**: 无
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": true
}
```

### 5.2 题目标签管理接口

#### 新增题目标签
- **接口地址**: `POST /api/subject/label/add`
- **请求参数**:
```
{
  "labelName": "集合",
  "categoryId": 1
}
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": true
}
```

#### 分页查询标签列表
- **接口地址**: `GET /api/subject/label/selectPage`
- **请求参数**:
```
labelName: 集合       // 标签名称（可选）
categoryId: 1          // 分类ID（可选）
pageNum: 1             // 页码（默认1）
pageSize: 10           // 每页条数（默认10）
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 100,
    "pages": 10,
    "result": [
      {
        "id": 1,
        "labelName": "集合",
        "categoryId": 1
      }
    ]
  }
}
```

#### 修改标签
- **接口地址**: `PUT /api/subject/label/update/{id}`
- **请求参数**:
```
{
  "labelName": "多线程",
  "categoryId": 1
}
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": true
}
```

#### 删除标签
- **接口地址**: `DELETE /api/subject/label/delete/{id}`
- **请求参数**: 无
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": true
}
```

### 5.3 题目信息管理接口

#### 新增题目
- **接口地址**: `POST /api/subject/info/add`
- **请求参数**:
```
{
  "subjectName": "Java中String是不可变的吗？",
  "subjectDifficult": 1,
  "settleName": "张三",
  "subjectType": 4,
  "subjectScore": 10,
  "subjectParse": "String是不可变的主要体现在...",
  "subjectAnswer": "String是不可变的原因有...",
  "categoryIds": [1],
  "labelIds": [1, 2]
}
```

对于选择题，还需要提供选项信息:
```
{
  "subjectName": "以下哪些是Java的基本数据类型？",
  "subjectDifficult": 1,
  "settleName": "张三",
  "subjectType": 2, // 多选题
  "subjectScore": 10,
  "subjectParse": "Java的基本数据类型包括...",
  "categoryIds": [1],
  "labelIds": [1],
  "optionList": [
    {
      "optionType": 1,
      "optionContent": "int",
      "isCorrect": 1
    },
    {
      "optionType": 2,
      "optionContent": "String",
      "isCorrect": 0
    },
    {
      "optionType": 3,
      "optionContent": "double",
      "isCorrect": 1
    },
    {
      "optionType": 4,
      "optionContent": "Boolean",
      "isCorrect": 0
    }
  ]
}
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": true
}
```

#### 分页查询题目列表(管理后台)
- **接口地址**: `GET /api/subject/info/selectPageToAdmin`
- **请求参数**:
```
subjectName: String            // 题目名称（模糊查询，可选）
subjectDifficult: 1            // 题目难度（可选）
subjectType: 1                 // 题目类型（可选）
pageNum: 1                     // 页码（默认1）
pageSize: 10                   // 每页条数（默认10）
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 100,
    "pages": 10,
    "result": [
      {
        "id": 1,
        "subjectName": "Java中String是不可变的吗？",
        "subjectDifficult": 1,
        "settleName": "张三",
        "subjectType": 4,
        "subjectScore": 10
      }
    ]
  }
}
```

#### 分页查询题目列表(面向用户)
- **接口地址**: `GET /api/subject/info/selectPageToUser`
- **请求参数**:
```
subjectName: String            // 题目名称（模糊查询，可选）
subjectDifficult: 1            // 题目难度（可选）
subjectType: 1                 // 题目类型（可选）
categoryId: 1                  // 分类ID（可选）
labelId: 1                     // 标签ID（可选）
pageNum: 1                     // 页码（默认1）
pageSize: 10                   // 每页条数（默认10）
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 100,
    "pages": 10,
    "result": [
      {
        "id": 1,
        "subjectName": "Java中String是不可变的吗？",
        "subjectDifficult": 1,
        "settleName": "张三",
        "subjectType": 4,
        "subjectScore": 10
      }
    ]
  }
}
```

#### 查询题目详情
- **接口地址**: `GET /api/subject/info/selectSubjectInfo`
- **请求参数**:
```
id: 1                          // 题目ID（必填）
```
- **响应结果**:
```
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "subjectName": "Java中String是不可变的吗？",
    "subjectDifficult": 1,
    "settleName": "张三",
    "subjectType": 4,
    "subjectScore": 10,
    "subjectParse": "String是不可变的主要体现在...",
    "subjectAnswer": "String是不可变的原因有...",
    "labelName": ["集合", "基础"]
  }
}
```

对于选择题，还会包含选项信息:
```
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "subjectName": "以下哪些是Java的基本数据类型？",
    "subjectDifficult": 1,
    "settleName": "张三",
    "subjectType": 2,
    "subjectScore": 10,
    "subjectParse": "Java的基本数据类型包括...",
    "optionList": [
      {
        "optionType": 1,
        "optionContent": "int",
        "isCorrect": 1
      },
      {
        "optionType": 2,
        "optionContent": "String",
        "isCorrect": 0
      },
      {
        "optionType": 3,
        "optionContent": "double",
        "isCorrect": 1
      },
      {
        "optionType": 4,
        "optionContent": "Boolean",
        "isCorrect": 0
      }
    ],
    "labelName": ["基础"]
  }
}
```

## 6. 总结

本系统通过工厂+策略模式的设计，实现了对不同类型题目的统一处理。每种题型的处理逻辑相互独立，新增题型时只需实现SubjectTypeHandler接口即可，保证了系统的扩展性和可维护性。
