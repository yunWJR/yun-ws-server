package com.yun.yunwsserver.module.group.dtovo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:40.
 */

@Data
@NoArgsConstructor
public class GroupDto {
    @NotBlank
    @Length(max = 200)
    private String clientGroupId;

    private List<String> clientUserId;

    private String remark;
}
