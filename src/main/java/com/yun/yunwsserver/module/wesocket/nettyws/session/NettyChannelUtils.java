package com.yun.yunwsserver.module.wesocket.nettyws.session;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * channel的工具类
 */
public final class NettyChannelUtils {

    private static AttributeKey<NettySession> SESSION_KEY = AttributeKey.valueOf("session");

    public static boolean addChannelSession(Channel channel, NettySession session) {
        Attribute<NettySession> sessionAttr = channel.attr(SESSION_KEY);
        return sessionAttr.compareAndSet(null, session);
    }

    public static NettySession getSessionBy(Channel channel) {
        Attribute<NettySession> sessionAttr = channel.attr(SESSION_KEY);
        return sessionAttr.get();
    }

    public static String getIp(Channel channel) {
        return ((InetSocketAddress) channel.remoteAddress()).getAddress().toString().substring(1);
    }

}
