package com.yun.yunwsserver.module.message.dtovo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yun
 * @createdOn: 2019-07-15 15:11.
 */

@Data
@NoArgsConstructor
public class IgnoreUserPlatformDto {
    private String userId;

    private String platform;
}
