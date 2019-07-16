package com.yun.yunwsserver.module.wesocket.nettyws;

import com.yun.yunwsserver.config.SpringContextUtil;
import com.yun.yunwsserver.config.WsProperties;
import com.yun.yunwsserver.module.wesocket.model.ImWsSession;
import com.yun.yunwsserver.module.wesocket.nettyws.session.NettySession;
import com.yun.yunwsserver.module.wesocket.service.ImWebSocketService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NettyIoHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(WebSocketServerHandshaker.class.getName());
    private WebSocketServerHandshaker handShaker;

    /**
     * 需要手动注入
     */
    private ImWebSocketService imService;

    private WsProperties wsProperties;

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * channel 通道 action 活跃的 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 添加
        System.out.println("客户端与服务端连接开启：" + ctx.channel().remoteAddress().toString());
    }

    /**
     * channel 通道 Inactive 不活跃的 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端关闭了通信通道并且不可以传输数据
     */
    @Override

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 移除
        System.out.println("客户端与服务端连接关闭：" + ctx.channel().remoteAddress().toString());
    }

    /**
     * channel 通道 Read 读取 Complete 完成 在通道读取完成后会在这个方法里通知，对应可以做刷新操作 ctx.flush()
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            System.out.println(1);
            handShaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            System.out.println("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(
                    String.format("%s frame types not supported", frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        System.out.println("服务端收到：" + request);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("%s received %s", ctx.channel(), request));
        }
        TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString() + ctx.channel().id() + "：" + request);

        // 返回
        ctx.channel().writeAndFlush(tws);
    }

    private void handlerWebSocketFrame2(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handShaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            System.out.println("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(
                    String.format("%s frame types not supported", frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        System.out.println("服务端2收到：" + request);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("%s received %s", ctx.channel(), request));
        }

        handleMessageOn(ctx, null, false, request);

        return;
        // TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString() + ctx.channel().pkId() + "：" + request);

        // 群发
        // Global.conversation.writeAndFlush(tws);

        // 返回【谁发的发给谁】
        // ctx.channel().writeAndFlush(tws);
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // 如果HTTP解码失败，返回HHTP异常
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        //获取url后置参数
        HttpMethod method = req.method();

        String uri = req.uri();
        // 构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://" + req.headers().get(HttpHeaders.Names.HOST) + uri, null, false);
        handShaker = wsFactory.newHandshaker(req);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), req);
        }

        if (method == HttpMethod.GET) {
            handleMessageOn(ctx, uri, true, null);
        }

        return;

        // String[] pa = uri.split("/");
        //
        // QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        // Map<String, List<String>> parameters = queryStringDecoder.parameters();
        // // System.out.println(parameters.get("request").get(0));
        // if (method == HttpMethod.GET && "/webssss".equals(uri)) {
        //     //....处理
        //     ctx.attr(AttributeKey.valueOf("itemType")).set("anzhuo");
        // } else if (method == HttpMethod.GET && "/websocket".equals(uri)) {
        //     //...处理
        //     ctx.attr(AttributeKey.valueOf("itemType")).set("live");
        // }
        //
        // // 构造握手响应返回，本机测试
        // WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
        //         "ws://" + req.headers().get(HttpHeaders.Names.HOST) + uri, null, false);
        // handShaker = wsFactory.newHandshaker(req);
        // if (handShaker == null) {
        //     WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        // } else {
        //     handShaker.handshake(ctx.channel(), req);
        // }
    }

    /**
     * exception 异常 Caught 抓住 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 接收客户端发送的消息 channel 通道 Read 读 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据。但是这个数据在不进行解码时它是ByteBuf类型的
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 传统的HTTP接入，Open 的时候处理逻辑
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) msg));
            // WebSocket接入
        } else if (msg instanceof WebSocketFrame) {
            System.out.println(handShaker.uri());

            handlerWebSocketFrame2(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleMessageOn(ChannelHandlerContext ctx, String uri, boolean isOpen, String message) {
        NettySession io = new NettySession(ctx.channel());

        if (isOpen) {
            String[] para = uri.split("/");
            if (para.length != 5) {
                getImService().wsErrorOn(null, null, null, new Throwable("dd"));

                return;
            }

            // 端点检查
            if (!getWsProperties().getWsEndpoint().equals(para[1])) {
                return;
            }

            String sessionId = para[2];
            String clientPt = para[3];
            String clientPara = para[4];
            getImService().wsOpen(new ImWsSession<>(io), sessionId, clientPt, clientPara);
        } else {
            getImService().wsMessageOn(new ImWsSession<>(io), null, null, message);
        }
    }

    /**
     * 该类不会自动注入，所以 bean 需要手动注入
     * @return
     */
    private ImWebSocketService getImService() {
        if (imService == null) {
            imService = SpringContextUtil.getBean(ImWebSocketService.class);
        }

        return imService;
    }

    private WsProperties getWsProperties() {
        if (wsProperties == null) {
            wsProperties = SpringContextUtil.getBean(WsProperties.class);
        }

        return wsProperties;
    }
}
