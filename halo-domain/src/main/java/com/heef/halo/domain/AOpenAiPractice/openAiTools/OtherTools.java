package com.heef.halo.domain.AOpenAiPractice.openAiTools;

import com.heef.halo.domain.basic.mapper.AuthUserMapper;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.heef.halo.domain.config.mybatisplus.MybatisPlusAllSqlLog.log;

@Component
public class OtherTools implements ChatTools{

    @Autowired
    private AuthUserMapper authUserMapper;
    @Tool(description = "查询当前网站的人数")
    public Long getWebUserCount() {
        log.info("调用getWebUserCount工具");
        return  authUserMapper.count(null);
    }
}
