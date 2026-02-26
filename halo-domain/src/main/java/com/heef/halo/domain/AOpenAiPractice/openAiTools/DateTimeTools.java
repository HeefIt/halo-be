package com.heef.halo.domain.AOpenAiPractice.openAiTools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.heef.halo.domain.config.mybatisplus.MybatisPlusAllSqlLog.log;

/**
 * 日期工具tools
 *
 * @author kyrie.huang
 * @date 2026/2/26 17:14
 */
@Component
public class DateTimeTools implements ChatTools{
    @Tool(description = "获取用户当前时区的日期和时间")//Get the current date and time in the user's timezone
    public String getCurrentDateTime() {
        log.info("调用getCurrentDateTime工具");
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }
}
