package com.yun.yunwsserver.module.clientuser.dtovo;

import com.yun.yunwsserver.module.clientuser.entity.ClientUser;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yun
 * @createdOn: 2019-07-12 13:27.
 */

@Data
@NoArgsConstructor
public class ClientUserVo {
    private Long id;

    private String extraUserId;

    private Long createTime;

    private Long updateTime;

    private String remark;

    public ClientUserVo(ClientUser cUser) {
        this.id = cUser.getId();
        this.extraUserId = cUser.getExtraUserId();
        this.createTime = cUser.getCreateTime();
        this.updateTime = cUser.getUpdateTime();
        this.remark = cUser.getRemark();
    }
}
