package com.yun.yunwsserver.module.group.entity;

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
public class GroupUserRl {

    @EmbeddedId
    @JsonUnwrapped
    private GroupUserRlPk pkId;

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

    public GroupUserRl(Long mgUserId, String clientGroupId, String clientUserId) {
        pkId = new GroupUserRlPk(mgUserId, clientGroupId, clientUserId);

        resetSsId();
    }

    public GroupUserRl(Group group, String clientUserId) {
        pkId = new GroupUserRlPk(group.getPkId().getMgUserId(), group.getPkId().getClientGroupId(), clientUserId);

        resetSsId();
    }

    @JsonIgnore
    private void resetSsId() {
        String sId = getPkId().getMgUserId().toString() + "-" + getPkId().getClientUserId();
        this.setSessionId(sId);
    }
}
