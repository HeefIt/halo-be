package com.heef.halo.domain.AOpenAiPractice.openAiQuery;

import lombok.Data;
import org.springframework.ai.tool.annotation.ToolParam;


/**
 * 题目查询参数--query
 *
 * @author kyrie.huang
 * @date 2026/2/26 11:49
 */

@Data
public class SubjectQuery {
    @ToolParam(description = "题目分类，如：数学、语文、英语")
    private String category;

    @ToolParam(description = "题目难度，如：简单、中等、困难")
    private String level;

    @ToolParam(description = "题目状态，如：正常、废弃")
    private String status;

    @ToolParam(description = "关键字搜索")
    private String keyword;

    @ToolParam(description = "分页页码，从1开始")
    private Integer pageNum = 1;

    @ToolParam(description = "每页数量")
    private Integer pageSize = 10;
}