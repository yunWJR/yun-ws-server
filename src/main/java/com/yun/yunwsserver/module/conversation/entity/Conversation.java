package com.yun.yunwsserver.module.conversation.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.yun.yunwsserver.module.conversation.dtovo.ConversationDto;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
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
 * @createdOn: 2019-07-12 14:22.
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ApiModel("群组")
public class Conversation {
    @EmbeddedId
    @JsonUnwrapped
    private ConversationPk pkId;

    @Column(nullable = false)
    @CreatedDate
    private Long createTime;

    @Column(nullable = false)
    @LastModifiedDate
    private Long updateTime;

    @Column
    private String remark;

    public Conversation(Long mgUserId, String clientGroupId) {
        pkId = new ConversationPk(mgUserId, clientGroupId);
    }

    public static Conversation newItem(MgUser mgUser, ConversationDto dto) {
        Conversation conversation = new Conversation(mgUser.getId(), dto.getClientGroupId());

        conversation.setRemark(dto.getRemark());

        return conversation;
    }
}
