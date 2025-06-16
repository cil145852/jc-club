package com.cjl.wx.handler;

import java.util.Map;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-16-16:18
 * @Description 处理微信消息接口
 */

public interface WxChatMsgHandler {
    WxChatMsgTypeEnum getMsgType();

    String handle(Map<String, String> msgMap);

     default String getTextMessage(String toUserName, String fromUserName, String content) {
        String textMessage = "<xml>\n" +
                "  <ToUserName><![CDATA[_to_user]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[_from_user]]></FromUserName>\n" +
                "  <CreateTime>_create_time</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[_content]]></Content>\n" +
                "</xml>";
        return textMessage
                .replace("_create_time", String.valueOf(System.currentTimeMillis()))
                .replace("_to_user", toUserName)
                .replace("_from_user", fromUserName)
                .replace("_content", content);
    }
}
