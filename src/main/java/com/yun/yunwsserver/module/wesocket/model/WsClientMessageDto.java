package com.yun.yunwsserver.module.wesocket.model;

import com.yun.yunwsserver.module.ClientPlatformType;
import lombok.Data;

/**
 * 客服端发送消息内容
 */
@Data
public class WsClientMessageDto implements java.io.Serializable {

    // region --Field

    private static final long serialVersionUID = 1L;

    /**
     * 客服端消息类型
     */
    private WsClientMessageType type = WsClientMessageType.Unknown;

    /**
     * seq，客服端指定，可采用递增生成
     */
    private Long seq = 0L;

    /**
     * 客服端消息
     */
    private String data;

    /**
     * 客服端平台
     */
    private ClientPlatformType platform;

    // endregion

    // region --Constructor

    // endregion

    // region --static method

    // endregion

    // region --Getter and Setter

    // endregion

    // region --Public method

    // endregion

    // region --private method

    // endregion

    // region --Other

    // endregion

}
