package com.yun.yunwsserver.module.wesocket.mq.dispatch;

import com.yun.yunwsserver.module.wesocket.model.ImWsSession;
import com.yun.yunwsserver.module.wesocket.model.WsRspMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImDispatchTask extends DispatchTask {

    private static Logger logger = LoggerFactory.getLogger(ImDispatchTask.class);

    private ImWsSession session;
    private WsRspMessage message;

    public static ImDispatchTask valueOf(int distributeKey, ImWsSession session, WsRspMessage message) {
        ImDispatchTask msgTask = new ImDispatchTask();
        msgTask.dispatchKey = distributeKey;
        msgTask.session = session;
        msgTask.message = message;

        return msgTask;
    }

    @Override
    public void run() {
        try {
            System.out.println("rsp run:" + message.toJsonStr());

            session.sendData(message.toJsonStr());
        } catch (Exception e) {
            logger.error("业务处理出现异常", e);
        }
    }
}
