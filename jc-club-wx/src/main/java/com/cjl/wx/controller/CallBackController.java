package com.cjl.wx.controller;

import com.cjl.wx.handler.WxChatMsgFactory;
import com.cjl.wx.handler.WxChatMsgHandler;
import com.cjl.wx.util.MessageUtil;
import com.cjl.wx.util.SHA1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-14-16:47
 * @Description 微信回调controller
 */
@Slf4j
@RestController
public class CallBackController {
    private static final String TOKEN = "addsfgjnvbs132dw";
    @Resource
    private WxChatMsgFactory wxChatMsgFactory;

    /**
     * 回调信息校验
     */
    @GetMapping("/callback")
    public String callback(
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr
    ) {
        log.info("signature:{}, timestamp:{}, nonce:{}, echostr:{}", signature, timestamp, nonce, echostr);
        String sha1 = SHA1.getSHA1(TOKEN, timestamp, nonce, "");
        if (signature.equals(sha1)) {
            return echostr;
        }
        return "unknown";
    }

    @PostMapping(value = "/callback", produces = "application/xml;charset=UTF-8")
    public String callback(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(value = "msg_signature", required = false) String msgSignature
    ) {
        String sha1 = SHA1.getSHA1(TOKEN, timestamp, nonce, "");
        if (signature.equals(sha1)) {
            Map<String, String> msgMap = MessageUtil.parseXml(requestBody);
            String msgType = msgMap.get("MsgType");
            String event = msgMap.get("Event");
            if (event != null) {
                msgType = msgType + "." + event;
            }
            log.info("msgType:{}", msgType);
            WxChatMsgHandler handler = wxChatMsgFactory.getHandler(msgType);
            if (handler == null) {
                log.info("unknown msgType:{}", msgType);
                return "unknown msgType";
            }
            return handler.handle(msgMap);
        }
        return "unknown";
    }
}
