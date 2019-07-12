package com.yun.yunwsserver.module.wesocket.model;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:03.
 */

public enum ClientUserStatus {
    /**
     *
     */
    Offline(0),
    Online(1),
    Hide(2);

    private final int status;

    private ClientUserStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
