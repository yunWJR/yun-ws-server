package com.yun.yunwsserver.module.wesocket.serverendpoint;

import com.yun.yunwsserver.config.SpringContextUtil;
import com.yun.yunwsserver.module.wesocket.model.ImWsSession;
import com.yun.yunwsserver.module.wesocket.service.ImWebSocketService;
import com.yun.yunwsserver.module.wesocket.session.ImSessionManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Socket 的Endpoint
 * para:平台
 * token:用户 token
 */
@ServerEndpoint(value = "/im/{sessionId}/{clientPt}/{clientPara}")
@Lazy(value = true)
@Component
public class ImWsServer {

    /**
     * 需要手动注入
     */
    private ImWebSocketService imService;

    /**
     * 该类不会自动注入，所以 bean 需要手动注入
     * @return
     */
    private ImWebSocketService getImService() {
        if (imService == null) {
            imService = SpringContextUtil.getBean(ImWebSocketService.class);
        }

        return imService;
    }

    /**
     * Socket的 OnOpen实现
     * @param session
     * @param sessionId
     * @param clientPt
     * @param clientPara
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("platform") String sessionId, @PathParam("platform") String clientPt, @PathParam("token") String clientPara) {
        // ClientPlatformType platformType = ClientPlatformType.valueOfStr(para);
        // if (platformType == null) {
        //     ImRspManager.sendRsp(session, WsRspMessageType.WsOpenMsgPlatformErr, null);
        //
        //     return;
        // }

        getImService().wsOpen(new ImWsSession<>(session), sessionId, clientPt, clientPara);

        // todo 测试信息
        print("当前在线用户：" + ImSessionManager.getOnlineNum());
        print("缓存中的用户个数：" + ImSessionManager.getCacheNum());
    }

    /**
     * Socket的 OnMessage实现
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, @PathParam("platform") String platform, @PathParam("token") String token, String message) {
        getImService().wsMessageOn(new ImWsSession<>(session), platform, token, message);
    }

    /**
     * Socket的 OnError实现
     * 具体错误处理流程待处理 todo
     * @param throwable
     */
    @OnError
    public void error(Session session, @PathParam("platform") String platform, @PathParam("token") String token, Throwable throwable) {
        getImService().wsErrorOn(new ImWsSession<>(session), platform, token, throwable);
        // print("连接错误:" + throwable.getMessage());

        // throwable.printStackTrace();
    }

    /**
     * Socket的 OnClose实现
     * @param session
     */
    @OnClose
    public void close(Session session, @PathParam("platform") String platform, @PathParam("token") String token) {
        getImService().wsCloseOn(new ImWsSession<>(session), platform, token);
    }

    private void print(String message) {
        System.out.println(message);
    }
}
