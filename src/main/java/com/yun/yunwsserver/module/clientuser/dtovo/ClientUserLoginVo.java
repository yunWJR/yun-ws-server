package com.yun.yunwsserver.module.clientuser.dtovo;

import com.yun.yunwsserver.module.clientuser.entity.ClientUserWsPlatform;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:16.
 */

@Data
@NoArgsConstructor
public class ClientUserLoginVo {
    private String clientUserId;

    private String platform;

    private String para;

    public ClientUserLoginVo(ClientUserWsPlatform newPt) {
        this.clientUserId = newPt.getPkId().getClientUserId();
        this.para = newPt.getPara();
        this.platform = newPt.getPlatform();
    }
}
