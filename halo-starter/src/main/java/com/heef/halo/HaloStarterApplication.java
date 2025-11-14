package com.heef.halo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * halo-springboot启动类
 *
 * @author heefM
 * @date 2025-10-31
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling  //开启定时
@Slf4j
public class HaloStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaloStarterApplication.class, args);
    }

}
