package com.yun.yunwsserver.module.wesocket.service;

import com.yun.yunwsserver.module.clientuser.service.ClientUserServiceImpl;
import com.yun.yunwsserver.module.message.service.MessageService;
import com.yun.yunwsserver.module.wesocket.model.*;
import com.yun.yunwsserver.module.wesocket.model.enumtype.WsRspMessageType;
import com.yun.yunwsserver.module.wesocket.mq.ImRspManager;
import com.yun.yunwsserver.module.wesocket.session.ImSessionManager;
import com.yun.yunwsserver.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: yun
 * @createdOn: 2018/7/25 16:54.
 */

@Service
public class ImWebSocketServiceImpl implements ImWebSocketService {

    // region --Field

    @Autowired
    private MessageService messageService;

    @Autowired
    private ClientUserServiceImpl clientUserService;

    // endregion

    // region --Public method

    @Override
    public void wsOpen(ImWsSession session, String sessionId, String clientPt, String clientPara) {
        // 处理异常
        boolean valid = clientUserService.isValidWsUser(sessionId, clientPt, clientPara);
        if (!valid) {
            // todo
            ImRspManager.sendRsp(session, WsRspMessageType.WsOpenErr, null);

            return;
        }

        // 保存user session
        boolean suc = ImSessionManager.addUser(sessionId, clientPt, clientPara, session);
        if (!suc) {
            ImRspManager.sendRsp(session, WsRspMessageType.WsOpenErr, null);
            return;
        }

        // 成功反馈
        ImRspManager.sendRsp(session, WsRspMessageType.WsOpenSuc, null);
    }

    @Override
    public void wsMessageOn(ImWsSession session, String platform, String token, String message) {
        // 缓存 session 检查
        ImWsSessionUser skUr = ImSessionManager.getSocketUserBySessionId(session.getId());
        if (skUr == null) {
            ImRspManager.sendErrByType(session, WsRspMessageType.MsgOnSessionErr);
            return;
        }

        // 数据解析
        WsClientMessageDto clMsg = JsonHelper.toObj(message, WsClientMessageDto.class);
        if (clMsg == null) {
            ImRspManager.sendErrByType(session, WsRspMessageType.MsgOnMsgErr);
            print("error message:" + message);
            return;
        }

        // 处理消息
        switch (clMsg.getType()) {
            case HeartPing: {
                updateHeart(session);
                return;
            }
            case HeartPong: {
                updateHeart(session);
                return;
            }
            case Unknown: {

            }
            break;
            default:
                break;
        }

        // 反馈错误
        ImRspManager.sendErrByType(session, WsRspMessageType.MsgOnTypeErr, clMsg);
    }

    @Override
    public void wsErrorOn(ImWsSession session, String platform, String token, Throwable throwable) {
        print("连接错误:" + throwable.getMessage());
    }

    @Override
    public void wsCloseOn(ImWsSession session, String platform, String token) {
        print("连接关闭:" + session.getId());

        ImWsSessionUser su = ImSessionManager.getSocketUserBySessionId(session.getId());
        if (su != null) {
            su.setStatus(ClientUserStatus.Offline);
            print("用户:" + su.getClientPt() + "  掉线");
        }

        ImSessionManager.removeSessionById(session.getId());

        // todo 测试信息
        print("当前在线用户：" + ImSessionManager.getOnlineNum());
        print("缓存中的用户个数：" + ImSessionManager.getCacheNum());
    }

    @Override
    public boolean pushMessage(String userId, String platform, WsRspMessage msg) {
        ImWsSessionUser ssUser = ImSessionManager.getSocketUserByUserId(userId, platform);

        if (ssUser == null) { //  || !ssUser.isOnline()
            return false;
        }

        print("发送消息：" + userId + ":" + msg.toJsonStr());

        return ImRspManager.sendRsp(ssUser.getSession(), msg);
    }

    @Override
    public void closeClient(Long id, String platform) {
        // todo
    }

    // endregion

    // region --private method

    private void updateHeart(ImWsSession session) {
        ImSessionManager.updateHeart(session);
    }

    private void print(String message) {
        System.out.println(message);
    }

    // endregion

    // region --Other

    // endregion
}
