package com.heef.halo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * halo-springboot启动类
 *
 * @author heefM
 * @date 2025-10-31
 */
@SpringBootApplication
@ComponentScan("com.heef.halo")  // 确保扫描到所有组件
@EnableCaching
@EnableScheduling  //开启定时 @Scheduled
@Slf4j
@MapperScan("com.heef.halo.domain.basic.mapper")
public class HaloStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaloStarterApplication.class, args);
    }

}
