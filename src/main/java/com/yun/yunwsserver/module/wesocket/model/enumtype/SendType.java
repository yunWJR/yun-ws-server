package com.yun.yunwsserver.module.wesocket.model.enumtype;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SendType {
    /**
     *
     */
    UNSENT(0), // 未发送
    SENT(1),// 已发送
    LEAVE(2); // 提掉线

    private final int type;

    private SendType(int type) {
        this.type = type;
    }

    @JsonValue
    public int getType() {
        return type;
    }
}
