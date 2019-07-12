package com.yun.yunwsserver.module.wesocket.service;

import com.yun.yunwsserver.module.wesocket.model.ImWsSession;
import com.yun.yunwsserver.module.wesocket.model.WsRspMessage;

/**
 * @author: yun
 * @createdOn: 2018/7/25 16:54.
 */

public interface ImWebSocketService {
    void wsOpen(ImWsSession session, String sessionId, String clientPt, String clientPara);

    void wsMessageOn(ImWsSession session, String platform, String token, String message);

    void wsErrorOn(ImWsSession session, String platform, String token, Throwable throwable);

    void wsCloseOn(ImWsSession session, String platform, String token);

    boolean pushMessage(String userId, String ignorePlatform, WsRspMessage msg);

    void closeClient(Long id, String platform);
}
