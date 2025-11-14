package com.heef.halo.domain.basic.service;

import com.heef.halo.domain.basic.entity.SubjectJudge;

import java.util.List;

public interface SubjectJudgeService {
    /**
     * 判断题插入
     * @param subjectJudge
     */
    void insert(SubjectJudge subjectJudge);

    /**
     * 查询判断题详情
     * @param subjectId
     * @return
     */
    List<SubjectJudge> queryJudge(int subjectId);
}
