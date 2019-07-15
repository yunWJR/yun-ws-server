package com.yun.yunwsserver.module.clientuser.dtovo;

import com.yun.yunwsserver.module.clientuser.entity.ClientUser;
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

    private String sessionId;

    private String wsPath;

    public ClientUserLoginVo(ClientUser cUser, ClientUserWsPlatform newPt) {
        this.sessionId = cUser.getSessionId();
        this.clientUserId = newPt.getPkId().getClientUserId();
        this.para = newPt.getPara();
        this.platform = newPt.getPkId().getPlatform();

        // todo
        this.wsPath = String.format("http://192.168.0.119:7191/im/%s/%s/%s", sessionId, platform, para);
    }
}
