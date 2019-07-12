package com.yun.yunwsserver.module.wesocket.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yun.base.IdGenerator.LongJson.LongJsonDeserializer;
import com.yun.base.IdGenerator.LongJson.LongJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * @author: yun
 * @createdOn: 2019-07-12 10:06.
 */

@Data
public class SendMessageDto {
    @NotNull
    @ApiModelProperty("对话id")
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long conversationId;

    @NotNull
    // @ApiModelProperty(ConversationType.des)
    private Integer conversationType;

    /**
     * 消息内容类型
     */
    @Column
    @NotNull
    // @ApiModelProperty(MessageType.des)
    private Integer messageType;

    /**
     * 各种类型消息的内容：文本、图片链接、语音链接、视频链接
     */
    @Column
    private String message;

    /**
     * 发送者，同步本地消息使用
     */
    @Column
    private String clientId;
}
