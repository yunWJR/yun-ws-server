package com.yun.yunwsserver.module.conversation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:35.
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ApiModel("用户信息")
public class ConversationUserRl {

    @EmbeddedId
    @JsonUnwrapped
    private ConversationUserRlPk pkId;

    @Column(nullable = false)
    @CreatedDate
    private Long createTime;

    @Column(nullable = false)
    @LastModifiedDate
    private Long updateTime;

    @Column
    private String remark;

    @Column
    private String sessionId;

    public ConversationUserRl(Long mgUserId, String clientGroupId, String clientUserId) {
        pkId = new ConversationUserRlPk(mgUserId, clientGroupId, clientUserId);

        resetSsId();
    }

    public ConversationUserRl(Conversation conversation, String clientUserId) {
        pkId = new ConversationUserRlPk(conversation.getPkId().getMgUserId(), conversation.getPkId().getClientGroupId(), clientUserId);

        resetSsId();
    }

    @JsonIgnore
    private void resetSsId() {
        String sId = getPkId().getMgUserId().toString() + "-" + getPkId().getClientUserId();
        this.setSessionId(sId);
    }
}
