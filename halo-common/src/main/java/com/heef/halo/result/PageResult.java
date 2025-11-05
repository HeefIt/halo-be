package com.heef.halo.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页返回实体类(负责返回数据给前端)
 *
 * @author heefM
 * @date 2025-
 */
@Data
public class PageResult<T> implements Serializable {

    /** 当前页码 */
    private Integer pageNo;

    /** 每页记录数 */
    private Integer pageSize;

    /** 总记录数 */
    private Integer total;

    /** 总页数 */
    private Integer totalPages;

    /** 查询结果数据列表 */
    private List<T> result;

    /** 起始记录位置（从1开始） */
    private Integer start;

    /** 结束记录位置 */
    private Integer end;

    /**
     * 私有构造器，强制使用建造者模式创建对象
     */
    private PageResult() {}

    /**
     * 创建建造者实例
     *
     * @param <T> 数据类型
     * @return 建造者实例
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * 建造者类 - 用于构建PageResult实例
     *
     * @param <T> 数据类型
     */
    public static class Builder<T> {
        /** 当前页码，默认第1页 */
        private Integer pageNo = 1;

        /** 每页记录数，默认20条 */
        private Integer pageSize = 20;

        /** 总记录数，默认0 */
        private Integer total = 0;

        /** 查询结果数据列表，默认空列表 */
        private List<T> result = Collections.emptyList();

        /**
         * 设置当前页码
         *
         * @param pageNo 当前页码
         * @return 建造者实例（支持链式调用）
         */
        public Builder<T> pageNo(Integer pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        /**
         * 设置每页记录数
         *
         * @param pageSize 每页记录数
         * @return 建造者实例（支持链式调用）
         */
        public Builder<T> pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        /**
         * 设置总记录数
         *
         * @param total 总记录数
         * @return 建造者实例（支持链式调用）
         */
        public Builder<T> total(Integer total) {
            this.total = total;
            return this;
        }

        /**
         * 设置查询结果数据列表
         *
         * @param result 数据列表
         * @return 建造者实例（支持链式调用）
         */
        public Builder<T> result(List<T> result) {
            this.result = result;
            return this;
        }

        /**
         * 构建PageResult实例
         * 自动计算总页数、起始位置和结束位置
         *
         * @return 构建完成的PageResult实例
         */
        public PageResult<T> build() {
            PageResult<T> pageResult = new PageResult<>();
            pageResult.pageNo = this.pageNo;
            pageResult.pageSize = this.pageSize;
            pageResult.total = this.total;
            pageResult.result = this.result;
            pageResult.totalPages = calculateTotalPages();
            pageResult.start = calculateStart();
            pageResult.end = calculateEnd();
            return pageResult;
        }

        /**
         * 计算总页数
         * 规则：总记录数为0时返回1页，否则向上取整
         *
         * @return 总页数
         */
        private Integer calculateTotalPages() {
            if (pageSize <= 0) return 0;
            return total == 0 ? 1 : (total + pageSize - 1) / pageSize;
        }

        /**
         * 计算起始记录位置
         * 规则：(当前页-1) * 每页大小 + 1
         *
         * @return 起始记录位置（从1开始）
         */
        private Integer calculateStart() {
            return (pageSize > 0 ? (pageNo - 1) * pageSize : 0) + 1;
        }

        /**
         * 计算结束记录位置
         * 规则：起始位置 + 每页大小 - 1，但不能超过总记录数
         *
         * @return 结束记录位置
         */
        private Integer calculateEnd() {
            return Math.min(calculateStart() + pageSize - 1, total);
        }
    }
}