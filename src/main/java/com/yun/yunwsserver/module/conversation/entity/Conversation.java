package com.yun.yunwsserver.module.conversation.entity;

import com.yun.yunwsserver.module.conversation.dtovo.ConversationDto;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
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
 * @createdOn: 2019-07-12 14:22.
 */

@Entity
@Table(name = "Conversation", uniqueConstraints = {
        @UniqueConstraint(name = "mgUser_extraCvs", columnNames = {"mgUserId", "extraCvsId"})})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ApiModel("群组")
public class Conversation {
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

    @Column
    private String remark;

    public Conversation(Long mgUserId, String extraCvsId) {
        this.mgUserId = mgUserId;
        this.extraCvsId = extraCvsId;
    }

    public static Conversation newItem(MgUser mgUser, ConversationDto dto) {
        Conversation conversation = new Conversation(mgUser.getId(), dto.getExtraCvsId());

        conversation.setRemark(dto.getRemark());

        return conversation;
    }
}
