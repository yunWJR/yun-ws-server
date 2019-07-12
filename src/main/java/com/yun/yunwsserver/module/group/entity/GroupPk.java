package com.yun.yunwsserver.module.group.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author: yun
 * @createdOn: 2019-07-12 15:00.
 */

@Data
@Embeddable
public class GroupPk implements Serializable {

    // region --Field

    @Column(nullable = false)
    private Long mgUserId;

    @Column(nullable = false)
    @Length(max = 200)
    private String clientGroupId;

    // endregion

    // region --Constructor

    public GroupPk() {
    }

    public GroupPk(Long mgUserId, String clientGroupId) {
        this.mgUserId = mgUserId;
        this.clientGroupId = clientGroupId;
    }

    // endregion
}
