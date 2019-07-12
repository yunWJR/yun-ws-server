package com.yun.yunwsserver.module.clientuser.dtovo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author: yun
 * @createdOn: 2019-07-12 13:27.
 */

@Data
@NoArgsConstructor
public class ClientUserDto {
    @NotBlank
    @Length(max = 200)
    private String clientUserId;

    @Length(max = 200)
    private String remark;
}
