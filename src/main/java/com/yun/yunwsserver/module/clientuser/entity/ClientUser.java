package com.yun.yunwsserver.module.clientuser.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.yun.yunwsserver.module.clientuser.dtovo.ClientUserDto;
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
 * @createdOn: 2019-07-12 10:00.
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ApiModel("用户信息")
public class ClientUser {
    @EmbeddedId
    @JsonUnwrapped
    private ClientUserPk pkId;

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

    public static ClientUser newUser(ClientUserDto dto, MgUser mgUser) {
        ClientUser user = new ClientUser();

        user.setPkId(new ClientUserPk(mgUser.getId(), dto.getClientUserId()));
        user.remark = dto.getRemark();

        String sId = user.getPkId().getMgUserId().toString() + "-" + user.getPkId().getClientUserId();
        user.setSessionId(sId);

        return user;
    }
}
