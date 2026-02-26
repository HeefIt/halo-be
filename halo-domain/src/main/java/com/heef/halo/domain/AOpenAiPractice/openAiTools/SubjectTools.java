package com.heef.halo.domain.AOpenAiPractice.openAiTools;

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


}
