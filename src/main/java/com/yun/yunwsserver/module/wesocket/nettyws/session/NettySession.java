package com.yun.yunwsserver.module.wesocket.nettyws.session;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty session
 */
@Data
public class NettySession {

    // region --Field
    private static final Logger logger = LoggerFactory.getLogger(NettySession.class);

    /**
     * 网络连接channel
     */
    private Channel channel;

    /**
     * ip地址
     */
    private String remoteIp;

    private boolean reconnected;

    // endregion

    // region --Constructor

    public NettySession() {

    }

    public NettySession(Channel channel) {
        this.channel = channel;
        this.remoteIp = NettyChannelUtils.getIp(channel);
    }

    /**
     * 向客户端发送消息
     * @param data
     */
    public void sendData(String data) {
        if (data == null) {
            return;
        }

        if (channel != null) {
            TextWebSocketFrame txt = new TextWebSocketFrame(data);
            channel.writeAndFlush(txt);
        }
    }

    // endregion

    // region --Public method

    public boolean isClose() {
        if (channel == null) {
            return true;
        }

        return !channel.isActive() || !channel.isOpen();
    }

    /**
     * 关闭session
     */
    public void close() {
        try {
            if (this.channel == null) {
                return;
            }

            if (channel.isOpen()) {
                channel.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return this.channel.id().toString();
    }

    public boolean isOpen() {
        return this.channel.isOpen();
    }

    // endregion

    // region --private method

    // endregion

    // region --Other

    // endregion

}
