package com.yun.yunwsserver.module.message.dtovo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yun.base.Util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author: yun
 * @createdOn: 2019-07-12 16:46.
 */

@Data
@NoArgsConstructor
public class MessageDto {
    private Long conversationId;

    private String extraConversationId;

    private String contentJson;

    private String contentString;

    private Object content;

    private List<IgnoreUserPlatformDto> ignoreList;

    @JsonIgnore
    public void reform() {
        if (StringUtil.hasCtn(contentJson)) {
            content = (Map) JSON.parse(contentJson);

            contentJson = null;
        }

        if (StringUtil.hasCtn(contentString)) {
            content = contentString;

            contentString = null;
        }
    }
}
