package com.yun.yunwsserver.module.clientuser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yun.base.Util.StringUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author: yun
 * @createdOn: 2019-07-12 13:55.
 */

@Entity
@Table(name = "ClientUserWsPlatform", uniqueConstraints = {
        @UniqueConstraint(name = "clientUser_platform", columnNames = {"extraUserId", "platform"})})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ApiModel("")
public class ClientUserWsPlatform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreatedDate
    private Long createTime;

    @Column(nullable = false)
    @LastModifiedDate
    private Long updateTime;

    @Column(nullable = false)
    private Long mgUserId;

    @Column(nullable = false)
    @Length(max = 200)
    private String extraUserId;

    @Column(nullable = false)
    private Long clientUserId;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String para;

    public static ClientUserWsPlatform newItem(ClientUser cUser, String platform) {
        ClientUserWsPlatform item = new ClientUserWsPlatform();

        item.setClientUserId(cUser.getId());
        item.setMgUserId(cUser.getMgUserId());
        item.setExtraUserId(cUser.getExtraUserId());
        item.setPlatform(platform);

        return item;
    }

    @JsonIgnore
    public void renewPara() {
        String newPara = RandomStringUtils.randomNumeric(10);
        while (StringUtil.hasCtn(para) && newPara.equals(para)) {
            newPara = RandomStringUtils.random(10);
        }

        para = newPara;
    }
}
