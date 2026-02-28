package com.heef.halo.domain.AOpenAiPractice.openAiTools;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heef.halo.domain.AOpenAiPractice.openAiQuery.SubjectQuery;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.domain.basic.mapper.SubjectInfoMapper;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.heef.halo.domain.config.mybatisplus.MybatisPlusAllSqlLog.log;

/**
 * 题目工具类-- tools
 *
 * @author kyrie.huang
 * @date 2026/2/26 11:11
 */
@Component
public class SubjectTools implements ChatTools{

    @Autowired
    private SubjectInfoMapper subjectInfoMapper;


    /**
     * 1. 获取题目总数
     */
    @Tool(description = "获取网站题目总数")
    public Long getTotalSubjectCount() {
        log.info("调用getTotalSubjectCount工具");
        return subjectInfoMapper.selectCount(null);
    }


    /**
     * 2. 获取指定类型的题目数量
     */
    @Tool(description = "获取指定类别的题目数量，如：单选,判断,简答,多选")
    public Long getSubjectCountByCategory(
            @ToolParam(description = "题目类型名称，可选值：单选,判断,简答,多选，必须准确填写") String subjectType
    ) {
        log.info("调用getSubjectCountByCategory工具，subjectType: {}", subjectType);

        // 参数校验
        if (subjectType == null || subjectType.trim().isEmpty()) {
            return 0L;
        }

        // 构建查询
        LambdaQueryWrapper<SubjectInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SubjectInfo::getSubjectType, subjectType.trim());

        return subjectInfoMapper.selectCount(wrapper);
    }


}
