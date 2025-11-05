package com.heef.halo.result;

import java.io.Serializable;

/**
 * 封装分页请求实体类(负责接收处理前端传递的参数)
 *
 * @author heefM
 * @date 2025-
 */
public class PageInfo implements Serializable {

    // 当前页
    public Integer pageNo = 1;

    // 每页记录数
    public Integer pageSize = 20;

    // 查询条件
    public Integer getPageNo() {
        if (pageNo == null || pageNo < 1) {
            return 1;
        }
        return pageNo;
    }

    // 查询条件
    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1 || pageSize > Integer.MAX_VALUE) {
            return 20;
        }
        return pageSize;
    }
}