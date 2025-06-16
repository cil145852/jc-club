package com.cjl.wx.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-16-16:24
 * @Description 微信·消息处理策略工厂
 */
@Component
public class WxChatMsgFactory implements InitializingBean {
    @Resource
    private List<WxChatMsgHandler> wxChatMsgHandlerList;
    private final Map<WxChatMsgTypeEnum, WxChatMsgHandler> wxChatMsgHandlerMap = new HashMap<>();

    /**
     * 根据消息类型获取处理策略
     *
     * @param msgType 消息类型
     * @return 处理策略
     */
    public WxChatMsgHandler getHandler(String msgType) {
        WxChatMsgTypeEnum chatMsgTypeEnum = WxChatMsgTypeEnum.getByMsgType(msgType);
        return wxChatMsgHandlerMap.get(chatMsgTypeEnum);
    }

    @Override
    public void afterPropertiesSet() {
        wxChatMsgHandlerList.forEach(wxChatMsgHandler -> wxChatMsgHandlerMap.put(wxChatMsgHandler.getMsgType(), wxChatMsgHandler));
    }
}
