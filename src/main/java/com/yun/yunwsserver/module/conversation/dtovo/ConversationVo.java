package com.yun.yunwsserver.module.conversation.dtovo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.yun.yunwsserver.module.conversation.entity.Conversation;
import com.yun.yunwsserver.module.conversation.entity.ConversationUserRl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:41.
 */

@Data
@NoArgsConstructor
public class ConversationVo {
    @JsonUnwrapped
    private Conversation conversation;

    private List<ConversationUserRl> userList;

    public ConversationVo(Conversation conversation, List<ConversationUserRl> userList) {
        this.conversation = conversation;
        this.userList = userList;
    }
}
