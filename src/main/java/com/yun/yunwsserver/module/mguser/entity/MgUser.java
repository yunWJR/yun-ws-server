package com.yun.yunwsserver.module.mguser.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yun.yunwsserver.module.mguser.dtovo.UserAccDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: yun
 * @createdOn: 2019-07-12 10:46.
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ApiModel("用户信息")
public class MgUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreatedDate
    private Long createTime;

    @Column(nullable = false)
    @LastModifiedDate
    private Long updateTime;

    @Embedded
    // @JsonUnwrapped
    private MgUserInfo info;

    @Column(nullable = false)
    @Length(min = 6, max = 40) // MD5 32位小写
    @ApiModelProperty("密码 （6-12），转为MD5 32位小写")
    @JsonIgnore
    private String password;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    @Column(unique = true)
    private String accessKey;

    @Column
    private String secretKey;

    @Column
    private String loginToken;

    public static MgUser newUser(UserAccDto dto) {
        MgUser user = new MgUser();
        user.setPassword(dto.getPassword());
        user.setInfo(new MgUserInfo());
        user.getInfo().setAcctName(dto.getAcctName());
        user.resetKeys();

        return user;
    }

    @JsonIgnore
    public void resetKeys() {
        // todo
    }

    @JsonIgnore
    public boolean isInValidUser() {
        return getInfo().getEnabled().equals(0);
    }
}
