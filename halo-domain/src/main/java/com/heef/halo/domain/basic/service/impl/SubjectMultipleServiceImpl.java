package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.mapper.SubjectJudgeMapper;
import com.heef.halo.domain.basic.mapper.SubjectMultipleMapper;
import com.heef.halo.domain.basic.service.SubjectMultipleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 多选题题服务service类
 *
 * @author heefM
 * @date 2025-11-12
 */
@Service
public class SubjectMultipleServiceImpl implements SubjectMultipleService {
    @Autowired
    private SubjectMultipleMapper subjectMultipleMapper;


}
