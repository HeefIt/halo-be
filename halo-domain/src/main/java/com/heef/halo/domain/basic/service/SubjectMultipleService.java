package com.heef.halo.domain.basic.service;

import com.heef.halo.domain.basic.entity.SubjectMultiple;

import java.util.List;

public interface SubjectMultipleService {
    /**
     * 批量插入多选题
     * @param subjectMultipleList
     */
    void InsertBacth(List<SubjectMultiple> subjectMultipleList);

    /**
     * 查询多选题详情信息
     * @param subjectId
     * @return
     */
    List<SubjectMultiple> queryMultiple(int subjectId);
}
