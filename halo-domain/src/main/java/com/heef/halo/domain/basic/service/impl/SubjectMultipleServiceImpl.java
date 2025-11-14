package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.entity.SubjectMultiple;
import com.heef.halo.domain.basic.mapper.SubjectJudgeMapper;
import com.heef.halo.domain.basic.mapper.SubjectMultipleMapper;
import com.heef.halo.domain.basic.service.SubjectMultipleService;
import com.heef.halo.domain.convert.SubjectMultipleConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private SubjectMultipleConvert subjectMultipleConvert;


    /**
     * 批量插入多选题
     * @param subjectMultipleList
     */
    @Override
    public void InsertBacth(List<SubjectMultiple> subjectMultipleList) {
        int insertedBatch = subjectMultipleMapper.insertBatch(subjectMultipleList);
    }

    @Override
    public List<SubjectMultiple> queryMultiple(int subjectId) {
        List<SubjectMultiple> subjectMultipleList=subjectMultipleMapper.queryMultiple(subjectId);
        return subjectMultipleList;
    }
}
