package com.yun.yunwsserver.module.wesocket.model;

import lombok.Data;

import java.util.Date;

/**
 * clientuser 与 sesion 的信息，用户服务端保存 clientuser 与 session 的关联
 */
@Data
public class ImWsSessionUser {

    // region --Field

    /**
     * webSocket session
     */
    private ImWsSession session;

    /**
     * clientUserId
     */
    private String clientPt;

    /**
     * 最后一次心跳时间
     */
    private Date lastHeartDate;

    /**
     * 用户状态
     */
    private ClientUserStatus status = ClientUserStatus.Offline;

    /**
     * 在调度时，是否关闭 session
     */
    private boolean shouldClose;

    /**
     * 用户平台
     */
    private String para;

    // endregion

    // region --Constructor

    public ImWsSessionUser() {
    }

    public ImWsSessionUser(String clientPt, String para, ImWsSession session) {
        this.session = session;
        this.clientPt = clientPt;
        this.para = para;
    }

    // endregion

    // region --Public method

    /**
     * 当前用户是否在线
     */
    public boolean isOnline() {
        return ClientUserStatus.Offline == getStatus();
    }

    // endregion
}
