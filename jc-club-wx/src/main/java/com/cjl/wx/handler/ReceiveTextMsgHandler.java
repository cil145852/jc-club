package com.cjl.wx.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-16-16:21
 * @Description
 */
@Component
@Slf4j
public class ReceiveTextMsgHandler implements WxChatMsgHandler{
    @Override
    public WxChatMsgTypeEnum getMsgType() {
        return WxChatMsgTypeEnum.TEXT;
    }

    @Override
    public String handle(Map<String, String> msgMap) {
        log.info("receive text msgMap:{}", msgMap);
        String content = msgMap.get("Content");
        String fromUserName = msgMap.get("FromUserName");
        String toUserName = msgMap.get("ToUserName");
        if (!"验证码".equals(content)) {
            String replyContent = "无法识别消息内容，您可以输入:\n" +
                    "1、验证码";
            return getTextMessage(fromUserName, toUserName, replyContent);
        }
        Random random = new Random();
        String verifyCode = String.valueOf(random.nextInt(10000));
        return getTextMessage(fromUserName, toUserName, "您当前的验证码是: " + verifyCode + " 五分钟内有效");
    }
}
