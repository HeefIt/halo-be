package com.heef.halo.domain.basic.service;

import com.heef.halo.domain.basic.entity.SubjectRadio;

import java.util.List;

public interface SubjectRadioService {
    /**
     * 批量新增单选题
     * @param subjectRadioList
     */
    void InsertBatch(List<SubjectRadio> subjectRadioList);

    /**
     * 查询当选题详情信息
     * @param subjectId
     * @return
     */
    List<SubjectRadio> queryRadio(int subjectId);
}
