package com.heef.halo.domain.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis的sql翻译显示
 *
 * @author heefM
 * @date 2025-10-31

 */
@Configuration
public class MybatisConfiguration {
    /**
     * 配置MybatisPlus拦截器，用于打印完整的SQL日志
     * 
     * @return MybatisPlusInterceptor 拦截器实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

        MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new MybatisPlusAllSqlLog());
        return mybatisPlusInterceptor;
    }

}
