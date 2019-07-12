package com.yun.yunwsserver.module.group.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.yun.yunwsserver.module.group.dtovo.GroupDto;
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
@ApiModel("")
public class Group {
    @EmbeddedId
    @JsonUnwrapped
    private GroupPk pkId;

    @Column(nullable = false)
    @CreatedDate
    private Long createTime;

    @Column(nullable = false)
    @LastModifiedDate
    private Long updateTime;

    @Column
    private String remark;



    public Group(Long mgUserId, String clientGroupId) {
        pkId = new GroupPk(mgUserId, clientGroupId);
    }

    public static Group newItem(MgUser mgUser, GroupDto dto) {
        Group group = new Group(mgUser.getId(), dto.getClientGroupId());

        group.setRemark(dto.getRemark());



        return group;
    }
}
