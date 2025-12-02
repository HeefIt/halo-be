//package com.heef.halo.domain.config;
//
//import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * xxl-job配置类
// *
// * @author heefM
// * @date 2025-11-27
// */
//@Configuration
//@Slf4j
//public class XxlJobConfig {
//
//    @Value("${xxl.job.admin.addresses}")
//    private String adminAddresses;
//
//    @Value("${xxl.job.accessToken}")
//    private String accessToken;
//
//    @Value("${xxl.job.executor.appname}")
//    private String appname;
//
//    @Value("${xxl.job.executor.port}")  // 关键修改：使用配置的端口
//    private int executorPort;
//
//    @Value("${xxl.job.executor.logpath}")
//    private String logPath;
//
//    @Value("${xxl.job.executor.logretentiondays}")
//    private int logRetentionDays;
//
//    @Bean
//    public XxlJobSpringExecutor xxlJobExecutor() {
//        log.info(">>>>>>>>>>> xxl-job config init.");
//        log.info("调度中心地址: {}", adminAddresses);
//        log.info("执行器AppName: {}", appname);
//        log.info("执行器端口: {}", executorPort);
//        log.info("AccessToken: {}", accessToken);
//
//        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
//        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
//        xxlJobSpringExecutor.setAppname(appname);
//        xxlJobSpringExecutor.setPort(executorPort);  // 关键修改：使用配置的端口
//        xxlJobSpringExecutor.setAccessToken(accessToken);
//        xxlJobSpringExecutor.setLogPath(logPath);
//        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
//
//        log.info(">>>>>>>>>>> xxl-job config completed.");
//        return xxlJobSpringExecutor;
//    }
//}