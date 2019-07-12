package com.yun.yunwsserver.module.wesocket.model.enumtype;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 服务端返回消息类型
 * @author: yun
 * @createdOn: 2018/7/24 下午10:58.
 */
public enum WsRspMessageType {
    /**
     *
     */
    Unknown(0),     // 未知

    WsOpenMin(0),
    WsOpenSuc(1),   // 新消息
    WsOpenErr(2),  // session 错误，请重新连接
    WsOpenTokenErr(3),  // session 错误，请重新连接
    WsOpenPlatformErr(4),  // session 错误，请重新连接,

    HeartPing(90),
    HeartPong(91),

    WsOpenMax(99),

    MsgOnMin(100),
    MsgOnSuc(100),
    MsgOnSessionErr(101),  // session 错误，请重新连接
    MsgOnMsgErr(102),
    MsgOnTypeErr(103),
    MsgOnMsgTypeErr(104),
    MsgOnMxn(199),

    MsgRspMin(200),
    NewMessage(201),
    NewCoupleMessage(202),
    NewGroupMessage(203),
    MsgRspMax(299),

    ServerMin(300),
    StateOffline(301),
    ServerErr(302),
    ServerMax(999),

    AllMax(99999);

    private final int type;

    private WsRspMessageType(int type) {
        this.type = type;
    }

    @JsonValue
    public int getType() {
        return type;
    }
}
