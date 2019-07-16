package com.yun.yunwsserver.module.wesocket.mq;

import com.yun.yunwsserver.config.SpringContextUtil;
import com.yun.yunwsserver.module.wesocket.model.ImWsSession;
import com.yun.yunwsserver.module.wesocket.model.WsClientMessageDto;
import com.yun.yunwsserver.module.wesocket.model.WsRspMessage;
import com.yun.yunwsserver.module.wesocket.model.WsRspMessageType;
import com.yun.yunwsserver.module.wesocket.mq.dispatch.ImDispatchTask;
import com.yun.yunwsserver.module.wesocket.mq.dispatch.ImDispatcher;

/**
 * Im 回复管理
 * 用于向客户端发送消息
 * @author: yun
 * @createdOn: 2018/7/26 09:14.
 */

// todo 用队列接收请求，然后异步发送
public class ImRspManager {

    public static void sendErrByType(ImWsSession session, WsRspMessageType msgType, WsClientMessageDto clientMessage) {
        sendErrByType(session, msgType, null, clientMessage.getSeq());
    }

    public static void sendErrByType(ImWsSession session, WsRspMessageType msgType) {
        sendErrByType(session, msgType, null, null, true);
    }

    public static void sendErrByType(ImWsSession session, WsRspMessageType msgType, Object data, Long seq, boolean isAsync) {
        WsRspMessage rspMessage = new WsRspMessage();
        rspMessage.setType(msgType);
        if (data != null) {
            rspMessage.setData(data);
        }
        if (seq != null) {
            rspMessage.setSeq(seq);
        }

        sendRsp(session, rspMessage, isAsync);
    }

    public static void sendErrByType(ImWsSession session, WsRspMessageType msgType, Object data, Long seq) {
        sendErrByType(session, msgType, data, seq, true);
    }

    public static void sendRsp(ImWsSession session, WsRspMessageType type, Object data, Long seq) {
        WsRspMessage rsp = new WsRspMessage();
        rsp.setType(type);
        rsp.setData(data);
        rsp.setSeq(seq);

        sendRsp(session, rsp);
    }

    public static void sendRsp(ImWsSession session, WsRspMessageType type, Object data) {
        sendRsp(session, type, data, 0L);
    }

    public static boolean sendRsp(ImWsSession session, WsRspMessage rspMessage) {
        return sendRsp(session, rspMessage, true);
    }

    public static boolean sendRsp(ImWsSession session, WsRspMessage rspMessage, boolean isAsync) {
        if (!session.isOpen()) {
            return false;
        }

        ImDispatchTask cmdTask = ImDispatchTask.valueOf(session.getDispatchKey(), session, rspMessage);
        SpringContextUtil.getBean(ImDispatcher.class).addTask(cmdTask);

        return true;
    }

    public static void sendServerErr(ImWsSession session, Object rst) {
        sendErrByType(session, WsRspMessageType.ServerErr, rst, 0L);
    }
}
