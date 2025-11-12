package com.heef.halo.domain.basic.handler.subject;


import com.heef.halo.enums.SubjectInfoTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目类型工厂处理类(统一处理策略类)
 *
 * @author heefM
 * @date 2025-04-15 17:11
 */
@Component
public class SubjectTypeHandlerFactory implements InitializingBean {

    //注入策略接口
    @Autowired
    private List<SubjectTypeHandler> subjectTypeHandlerList;

    /**
     * 定义一个map,来解接收策略和枚举 ,接下来方便操作
     */
    private Map<SubjectInfoTypeEnum, SubjectTypeHandler> handlerMap = new HashMap<>();

    /**
     * 根据策略的枚举来实现
     * @param subjectType
     * @return
     */
    public SubjectTypeHandler getHandler(int subjectType) {
        SubjectInfoTypeEnum subjectInfoTypeEnum = SubjectInfoTypeEnum.getByCode(subjectType);
        return handlerMap.get(subjectInfoTypeEnum);
    }

    /**
     * 这是 InitializingBean 接口要求实现的方法
     * 在 spring 容器完成属性注入后自动调用
     * 初始化 handlerMap，建立题目类型枚举(SubjectInfoTypeEnum)到处理器(SubjectTypeHandler)的映射关系
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        for (SubjectTypeHandler subjectTypeHandler : subjectTypeHandlerList) {
            handlerMap.put(subjectTypeHandler.getHandlerType(), subjectTypeHandler);
        }
    }

}