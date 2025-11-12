package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.mapper.SubjectJudgeMapper;
import com.heef.halo.domain.basic.mapper.SubjectRadioMapper;
import com.heef.halo.domain.basic.service.SubjectRadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 单选题service服务类
 *
 * @author heefM
 * @date 2025-11-12
 */
@Service
public class SubjectRadioServiceImpl implements SubjectRadioService {
    @Autowired
    private SubjectRadioMapper subjectRadioMapper;
}
