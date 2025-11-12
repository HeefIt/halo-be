package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.mapper.SubjectJudgeMapper;
import com.heef.halo.domain.basic.service.SubjectJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
