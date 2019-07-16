package com.yun.yunwsserver.module.wesocket.model;

import com.yun.yunwsserver.util.JsonHelper;
import lombok.Data;

/**
 * @author: yun
 * @createdOn: 2019-07-12 10:06.
 */

@Data
public class PushDataDto implements java.io.Serializable {

    // region --Field

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    private WsRspMessageType type = WsRspMessageType.Unknown;

    /**
     * 返回数据
     */
    private Object data;

    // endregion

    // region --Constructor

    public PushDataDto() {
    }

    public PushDataDto(WsRspMessageType type) {
        this.type = type;
    }

    public PushDataDto(WsRspMessageType type, Object data) {
        this.type = type;
        this.data = data;
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