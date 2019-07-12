package com.yun.yunwsserver.module.clientuser.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.yun.base.Util.StringUtil;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author: yun
 * @createdOn: 2019-07-12 13:55.
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ApiModel("")
public class ClientUserWsPlatform {
    @EmbeddedId
    @JsonUnwrapped
    private ClientUserPk pkId;

    @Column(nullable = false)
    @CreatedDate
    private Long createTime;

    @Column(nullable = false)
    @LastModifiedDate
    private Long updateTime;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String para;

    public static ClientUserWsPlatform newItem(ClientUser cUser, String platform) {
        ClientUserWsPlatform item = new ClientUserWsPlatform();

        item.setPkId(cUser.getPkId());
        item.setPlatform(platform);

        return item;
    }

    public void renewPara() {
        String newPara = RandomStringUtils.random(10);
        while (StringUtil.hasCtn(para) && newPara.equals(para)) {
            newPara = RandomStringUtils.random(10);
        }

        para = newPara;
    }
}
