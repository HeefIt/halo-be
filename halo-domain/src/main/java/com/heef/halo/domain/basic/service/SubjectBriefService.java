package com.heef.halo.domain.basic.service;

import com.heef.halo.domain.basic.entity.SubjectBrief;

public interface SubjectBriefService {
    /**
     * 简答题题目插入
     * @param subjectBrief
     */
    void insert(SubjectBrief subjectBrief);

    /**
     * 根据题目id查询简答题具体信息
     * @param subjectId
     * @return
     */
    SubjectBrief queryBrief(int subjectId);
}
