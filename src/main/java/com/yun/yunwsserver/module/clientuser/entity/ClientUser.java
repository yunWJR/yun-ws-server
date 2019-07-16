package com.yun.yunwsserver.module.clientuser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yun.yunwsserver.module.Utils;
import com.yun.yunwsserver.module.clientuser.dtovo.ClientUserDto;
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
 * @createdOn: 2019-07-12 10:00.
 */

@Entity
@Table(name = "client_user", uniqueConstraints = {
        @UniqueConstraint(name = "mgUser_extraUser", columnNames = {"mgUserId", "extraUserId"})})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ApiModel("用户信息")
public class ClientUser {
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
    private String extraUserId;

    @Column
    private String remark;

    @Column
    private String sessionId;

    public static ClientUser newUser(ClientUserDto dto, MgUser mgUser) {
        ClientUser user = new ClientUser();

        if (mgUser != null) {
            user.setMgUserId(mgUser.getId());
        }

        if (dto != null) {
            user.setExtraUserId(dto.getExtraUserId());
            user.remark = dto.getRemark();
        }

        user.setSessionId(Utils.userSessionId(user.id, user.mgUserId, user.extraUserId));

        return user;
    }

    @JsonIgnore
    public void resetSsId() {
        this.setSessionId(Utils.userSessionId(this.id, this.mgUserId, this.extraUserId));
    }
}
