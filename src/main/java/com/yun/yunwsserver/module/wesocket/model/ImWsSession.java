package com.yun.yunwsserver.module.wesocket.model;

import com.yun.yunwsserver.module.wesocket.nettyws.session.NettySession;
import lombok.Data;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yun
 * @createdOn: 2018/8/9 08:52.
 */

@Data
public class ImWsSession<T> {

    // region --Field

    /**
     * session 对象
     */
    private T session;

    /**
     * 业务分发索引
     */
    private int dispatchKey;

    /**
     * 业务分发索引生成器
     */
    private AtomicInteger dpKeyGenerator = new AtomicInteger();

    /**
     * 拓展属性
     */
    private Map<String, Object> attrs = new HashMap<>();

    // endregion

    // region --Constructor

    public ImWsSession() {
        this.dispatchKey = dpKeyGenerator.getAndIncrement();
    }

    public ImWsSession(T session) {
        this();

        this.session = session;
    }

    // endregion

    // region --Public method

    public String getId() {
        if (this.session instanceof Session) {
            return ((Session) this.session).getId();
        }

        if (this.session instanceof NettySession) {
            return ((NettySession) this.session).getId();
        }

        return null;
    }

    public boolean isOpen() {
        if (this.session instanceof Session) {
            return ((Session) this.session).isOpen();
        }

        if (this.session instanceof NettySession) {
            return ((NettySession) this.session).isOpen();
        }

        return false;
    }

    public void sendData(String toJsonStr) {
        if (this.session instanceof Session) {
            Session ss = (Session) session;

            ss.getAsyncRemote().sendText(toJsonStr);

            return;
        }

        if (this.session instanceof NettySession) {
            NettySession isS = (NettySession) session;
            isS.sendData(toJsonStr);
        }
    }

    // endregion
}
