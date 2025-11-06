package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.dto.subjectDTO.SubjectBriefDTO;
import com.heef.halo.domain.basic.mapper.*;
import com.heef.halo.domain.basic.service.ShareService;
import com.heef.halo.domain.basic.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * suject-题目业务层
 *
 * @author heefM
 * @date 2025-11-05
 */
@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectInfoMapper subjectInfoMapper;

    @Autowired
    private SubjectCategoryMapper subjectCategoryMapper;

    @Autowired
    private SubjectLabelMapper subjectLabelMapper;

    @Autowired
    private SubjectLikedMapper subjectLikedMapper;

    @Autowired
    private SubjectRadioMapper subjectRadioMapper;

    @Autowired
    private SubjectMultipleMapper subjectMultipleMapper;

    @Autowired
    private SubjectJudgeMapper subjectJudgeMapper;

    @Autowired
    private SubjectBriefMapper subjectBriefMapper;




}
