package com.yun.yunwsserver.module.mguser.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: yun
 * @createdOn: 2019-07-12 10:46.
 */

@Embeddable
@ApiModel("用户信息")
@Data
public class MgUserInfo {
    @Column(nullable = false)
    @NotNull
    @ApiModelProperty("是否启用:0-否，1-是")
    private Integer enabled = 0;

    @Column(unique = true, nullable = false, length = 16) // 不重复,不能更改
    @NotEmpty
    @Length(min = 8, max = 16)
    @ApiModelProperty("用户名（8-16）-不能更改")
    private String acctName;

    @Column
    @ApiModelProperty("电话（6-11）")
    private String phone;

    @Column
    @ApiModelProperty("姓名（2-10）")
    private String name;

    @Column
    @Length(max = 100)
    @ApiModelProperty("头像")
    private String headerUrl;

    @Column
    @ApiModelProperty("邮箱(40)")
    private String email;
}
