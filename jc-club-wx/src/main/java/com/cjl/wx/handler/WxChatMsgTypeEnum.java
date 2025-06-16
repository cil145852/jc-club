package com.cjl.wx.handler;

import lombok.Getter;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-16-16:12
 * @Description 微信消息类型枚举
 */
@Getter
public enum WxChatMsgTypeEnum {
    SUBSCRIBE("event.subscribe", "用户订阅消息"),
    TEXT("text", "文本消息");
    private final String msgType;
    private final String desc;

    WxChatMsgTypeEnum(String msgType, String desc) {
        this.msgType = msgType;
        this.desc = desc;
    }

    public static WxChatMsgTypeEnum getByMsgType(String msgType) {
        for (WxChatMsgTypeEnum value : WxChatMsgTypeEnum.values()) {
            if (value.getMsgType().equals(msgType)) {
                return value;
            }
        }
        return null;
    }
}
