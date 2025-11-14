package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.entity.SubjectBrief;
import com.heef.halo.domain.basic.mapper.SubjectBriefMapper;
import com.heef.halo.domain.basic.service.SubjectBriefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 简答题服务service类
 *
 * @author heefM
 * @date 2025-11-12
 */
@Service
public class SubjectBriefServiceImpl implements SubjectBriefService {

    @Autowired
    private SubjectBriefMapper subjectBriefMapper;

    /**
     * 简答题题目插入
     * @param subjectBrief
     */
    @Override
    public void insert(SubjectBrief subjectBrief) {
        subjectBriefMapper.insert(subjectBrief);
    }

    /**
     * 根据题目id查询简答题具体信息
     * @param subjectId
     * @return
     */
    @Override
    public SubjectBrief queryBrief(int subjectId) {
        SubjectBrief subjectBrief = subjectBriefMapper.queryBrief(subjectId);
        return subjectBrief;
    }

}
