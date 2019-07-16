package com.yun.yunwsserver.module.clientuser.dtovo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    private String extraUserId;

    private String platform;

    private String para;

    private String sessionId;

    private String wsPath;

    public ClientUserLoginVo(ClientUser cUser, ClientUserWsPlatform newPt) {
        this.id = cUser.getId();
        this.sessionId = cUser.getSessionId();
        this.extraUserId = newPt.getExtraUserId();
        this.para = newPt.getPara();
        this.platform = newPt.getPlatform();
    }

    @JsonIgnore
    public void creatPath(String host, String endpoint) {
        if (host == null || endpoint == null) {
            return;
        }

        this.wsPath = String.format("%s/%s/%s/%s/%s", host, endpoint, sessionId, platform, para);
    }
}
