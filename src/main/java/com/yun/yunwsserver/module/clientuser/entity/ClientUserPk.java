package com.yun.yunwsserver.module.clientuser.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:43.
 */

@Data
@Embeddable
public class ClientUserPk implements Serializable {

    // region --Field

    @Column(nullable = false)
    private Long mgUserId;

    @Column(nullable = false)
    @Length(max = 200)
    private String clientUserId;

    // endregion

    // region --Constructor

    public ClientUserPk() {
    }

    public ClientUserPk(Long mgUserId, String clientUserId) {
        this.mgUserId = mgUserId;
        this.clientUserId = clientUserId;
    }

    // endregion
}
