package com.yun.yunwsserver.module.message.service;

import com.yun.yunwsserver.module.BaseServiceImpl;
import com.yun.yunwsserver.module.conversation.entity.QGroupUserRl;
import com.yun.yunwsserver.module.message.dtovo.MessageDto;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
import com.yun.yunwsserver.module.wesocket.model.WsRspMessage;
import com.yun.yunwsserver.module.wesocket.model.enumtype.WsRspMessageType;
import com.yun.yunwsserver.module.wesocket.service.ImWebSocketService;
import com.yun.yunwsserver.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yun
 * @createdOn: 2019-07-12 10:08.
 */

@Service
public class MessageService extends BaseServiceImpl {
    @Autowired
    private ImWebSocketService webSocketService;

    public void pushMessage(MessageDto dto) {
        MgUser mgUser = RequestUtil.getAccessUser();

        QGroupUserRl userRl = QGroupUserRl.groupUserRl;
        List<String> userSsIdList = queryFactory.select(userRl.sessionId)
                .from(userRl)
                .where(userRl.pkId.mgUserId.eq(mgUser.getId())
                        .and(userRl.pkId.clientGroupId.eq(dto.getClientGroupId())))
                .fetch();

        for (String ssId : userSsIdList) {
            webSocketService.pushMessage(ssId,
                    null,
                    new WsRspMessage(WsRspMessageType.NewMessage, dto.getContent()));
        }
    }
}
