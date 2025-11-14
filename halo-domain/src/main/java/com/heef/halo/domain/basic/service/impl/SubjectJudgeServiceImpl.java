package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.entity.SubjectJudge;
import com.heef.halo.domain.basic.mapper.SubjectJudgeMapper;
import com.heef.halo.domain.basic.service.SubjectJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 判断题服务service类
 *
 * @author heefM
 * @date 2025-11-12
 */
@Service
public class SubjectJudgeServiceImpl implements SubjectJudgeService {
    @Autowired
    private SubjectJudgeMapper subjectJudgeMapper;

    /**
     * 判断题题目插入
     * @param subjectJudge
     */
    @Override
    public void insert(SubjectJudge subjectJudge) {
        subjectJudgeMapper.insert(subjectJudge);
    }


    /**
     * 查询判断题详情
     * @param subjectId
     * @return
     */
    @Override
    public List<SubjectJudge> queryJudge(int subjectId) {
        List<SubjectJudge> subjectJudgeList=subjectJudgeMapper.queryJudge(subjectId);
        return subjectJudgeList;
    }

}
