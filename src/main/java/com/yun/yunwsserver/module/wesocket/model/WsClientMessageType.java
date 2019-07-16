package com.yun.yunwsserver.module.wesocket.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 客服端消息类型
 * @author: yun
 * @createdOn: 2018/7/24 下午10:17.
 */

public enum WsClientMessageType {
    /**
     * 未知
     */
    Unknown(0),

    /**
     * 心跳信号Ping
     */
    HeartPing(1),

    /**
     * 心跳信号Pong
     */
    HeartPong(2),

    WsClientMessageTypeMax(100);

    private final int type;

    private WsClientMessageType(int type) {
        this.type = type;
    }

    @JsonValue
    public int getType() {
        return type;
    }
}
