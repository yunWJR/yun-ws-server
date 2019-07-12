package com.yun.yunwsserver.module.wesocket.serverendpoint;

import com.yun.yunwsserver.util.JsonHelper;
import com.yun.yunwsserver.module.wesocket.model.ImWsSessionUser;
import com.yun.yunwsserver.module.wesocket.session.ImSessionManager;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 辅助测试类
 */
@ServerEndpoint(value = "/imTest")
@Component
public class ImWsTest {
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WeSocket Open");
    }

    @OnMessage
    public void onMessage(Long id, Session session) {
        String re = "offline";
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        if (id != 0) {

        }
        result.put("status:", re);
        try {
            session.getBasicRemote().sendText(JsonHelper.toStr(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void error(Throwable t) {
        System.out.println("WeSocket连接错误:" + t.getMessage());
        t.printStackTrace();
    }

    @OnClose
    public void close(Session session) {
        ImWsSessionUser su = ImSessionManager.getSocketUserBySessionId(session.getId());
        if (su != null) {
             // todo
            // su.setStatus(ClientUserStatus.Offline);
            System.out.println("WeSocket关闭： 用户：" + su.getClientPt() + " 掉线");
        }
    }
}
