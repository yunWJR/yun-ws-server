package com.yun.yunwsserver.module.mguser.dtovo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author: yun
 * @createdOn: 2019-07-12 11:18.
 */

@Data
@NoArgsConstructor
public class MgUserAccDto {

    @NotBlank
    @Length(min = 6, max = 40) // MD5 32位小写
    @ApiModelProperty("密码 （6-12），转为MD5 32位小写")
    private String password;

    @NotBlank
    @Length(min = 8, max = 16)
    @ApiModelProperty("用户名（8-16）-不能更改")
    private String acctName;
}
