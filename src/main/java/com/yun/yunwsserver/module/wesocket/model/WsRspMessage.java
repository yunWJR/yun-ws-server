package com.yun.yunwsserver.module.wesocket.model;

import com.yun.yunwsserver.util.JsonHelper;
import lombok.Data;

/**
 * 反馈消息内容
 * @author: yun
 * @createdOn: 2018/7/24 下午10:57.
 */
@Data
public class WsRspMessage implements java.io.Serializable {

    // region --Field

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    private WsRspMessageType type = WsRspMessageType.Unknown;

    /**
     * 客服端的 seq，如果客户端传了 seq，则返回客服端的 seq，如没有，则 seq=0
     */
    private Long seq = 0L;

    /**
     * 返回数据
     */
    private Object data;

    // endregion

    // region --Constructor

    public WsRspMessage() {
    }

    public WsRspMessage(WsRspMessageType type) {
        this.type = type;
    }

    public WsRspMessage(PushDataDto data) {
        this.type = data.getType();
        this.data = data.getData();
    }

    public WsRspMessage(WsRspMessageType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public WsRspMessage(WsRspMessageType type, Long seq) {
        this.type = type;
        this.seq = seq;
    }

    // endregion

    // region --Public method

    /**
     * 转换为 string
     * @return
     */
    public String toJsonStr() {
        return JsonHelper.toStr(this);
    }

    // endregion

    // region --private method

    // endregion

    // region --Other

    // endregion
}
