package com.yun.yunwsserver.module.clientuser.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author: yun
 * @createdOn: 2019-07-15 15:46.
 */

@Data
@Embeddable
public class ClientUserWsPlatformPk implements Serializable {

    // region --Field

    @Column(nullable = false)
    private Long mgUserId;

    @Column(nullable = false)
    @Length(max = 200)
    private String clientUserId;

    @Column(nullable = false)
    private String platform;

    // endregion

    // region --Constructor

    public ClientUserWsPlatformPk() {
    }

    public ClientUserWsPlatformPk(Long mgUserId, String clientUserId) {
        this.mgUserId = mgUserId;
        this.clientUserId = clientUserId;
    }

    // endregion
}
