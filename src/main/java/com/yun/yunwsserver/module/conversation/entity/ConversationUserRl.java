package com.yun.yunwsserver.module.conversation.entity;

import com.yun.yunwsserver.module.clientuser.entity.ClientUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:35.
 */

@Entity
@Table(name = "ConversationUserRl", uniqueConstraints = {
        @UniqueConstraint(name = "cvs_user", columnNames = {"extraUserId", "conversationId"})})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ApiModel("群组用户关系")
public class ConversationUserRl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreatedDate
    private Long createTime;

    @Column(nullable = false)
    @LastModifiedDate
    private Long updateTime;

    @Column(nullable = false)
    private Long mgUserId;

    @Column(nullable = false)
    @Length(max = 200)
    private String extraCvsId;

    @Column(nullable = false)
    @Length(max = 200)
    private String extraUserId;

    @Column(nullable = false)
    private Long clientUserId;

    @Column(nullable = false)
    private Long conversationId;

    @Column
    private String remark;

    @Column
    private String sessionId;

    public ConversationUserRl(Conversation conversation, ClientUser cUser) {
        this.mgUserId = conversation.getMgUserId();
        this.extraCvsId = conversation.getExtraCvsId();
        this.conversationId = conversation.getId();

        this.clientUserId = cUser.getId();
        this.extraUserId = cUser.getExtraUserId();
        this.sessionId = cUser.getSessionId();
    }
}
