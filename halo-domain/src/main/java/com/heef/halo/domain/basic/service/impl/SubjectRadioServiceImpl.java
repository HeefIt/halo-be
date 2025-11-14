package com.heef.halo.domain.basic.service.impl;

import com.heef.halo.domain.basic.entity.SubjectRadio;
import com.heef.halo.domain.basic.mapper.SubjectJudgeMapper;
import com.heef.halo.domain.basic.mapper.SubjectRadioMapper;
import com.heef.halo.domain.basic.service.SubjectRadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 批量新增单选题
     * @param subjectRadioList
     */
    @Override
    public void InsertBatch(List<SubjectRadio> subjectRadioList) {
        int insertedBatch = subjectRadioMapper.insertBatch(subjectRadioList);
    }


    /**
     * 查询当选题详情信息
     * @param subjectId
     * @return
     */
    @Override
    public List<SubjectRadio> queryRadio(int subjectId) {
        List<SubjectRadio> subjectRadioList=subjectRadioMapper.queryRadio(subjectId);
        return subjectRadioList;
    }
}
