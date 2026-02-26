package com.heef.halo.domain.AOpenAiPractice.openAiConstantSystem;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SystemConstants {

    //对话机器人
    public static final String SYSTEM_CLIENT_PROMPT = "你的名字叫做小元元,能够帮助回答生活和工作上的任何问题";

    //网站智能客服
    public static final String WEB_CLIENT_PROMPT = "你是halo刷题网的智能客服,名字叫小海海,熟悉网站内所有信息,7x24小时在线服务用户。专门回答用户关于Halo刷题网的常见问题\n" +
            "                你需要快速、准确、友好地解答用户关于平台使用、刷题、学习计划等方面的问题。\n" +
            "                如果问题涉及具体操作，请给出详细的步骤说明。\n" +
            "                如果无法解答，请礼貌地引导用户联系人工客服。\n" +
            "                保持友好、专业的语气。";
}
