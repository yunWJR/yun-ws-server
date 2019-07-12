package com.yun.yunwsserver.module.group.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yun.base.IdGenerator.LongJson.LongJsonDeserializer;
import com.yun.base.IdGenerator.LongJson.LongJsonSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:35.
 */

@Data
@Embeddable
public class GroupUserRlPk implements Serializable {

    // region --Field

    @Column(nullable = false)
    private Long mgUserId;

    @Column(nullable = false)
    @Length(max = 200)
    private String clientGroupId;

    @Column(nullable = false)
    @Length(max = 200)
    private String clientUserId;

    // endregion

    // region --Constructor

    public GroupUserRlPk() {
    }

    public GroupUserRlPk(Long mgUserId, String clientGroupId,String clientUserId) {
        this.mgUserId = mgUserId;
        this.clientGroupId = clientGroupId;
        this.clientUserId = clientUserId;
    }

    // endregion
}
