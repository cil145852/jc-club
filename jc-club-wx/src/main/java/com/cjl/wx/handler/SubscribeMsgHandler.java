package com.cjl.wx.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-16-16:19
 * @Description
 */
@Component
@Slf4j
public class SubscribeMsgHandler implements WxChatMsgHandler {
    @Override
    public WxChatMsgTypeEnum getMsgType() {
        return WxChatMsgTypeEnum.SUBSCRIBE;
    }

    @Override
    public String handle(Map<String, String> msgMap) {
        log.info("subscribe msgMap:{}", msgMap);
        String fromUserName = msgMap.get("FromUserName");
        String toUserName = msgMap.get("ToUserName");
        return getTextMessage(fromUserName, toUserName, "谢谢你的关注，欢迎来到鸡翅社区");
    }
}
