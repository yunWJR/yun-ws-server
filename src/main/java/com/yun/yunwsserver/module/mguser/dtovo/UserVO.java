package com.yun.yunwsserver.module.mguser.dtovo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
import com.yun.yunwsserver.module.mguser.entity.MgUserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yun
 * @createdOn: 2019-07-12 11:08.
 */

@Data
@NoArgsConstructor
public class UserVO {
    private Long id;

    private Long createTime;

    @JsonUnwrapped
    private MgUserInfo info;

    private String accessKey;

    private String secretKey;

    public UserVO(MgUser mgUser) {
        this.id = mgUser.getId();
        this.createTime = mgUser.getCreateTime();
        this.info = mgUser.getInfo();

        this.accessKey = mgUser.getAccessKey();
        this.secretKey = mgUser.getSecretKey();
    }
}
