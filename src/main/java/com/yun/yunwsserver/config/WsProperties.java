package com.yun.yunwsserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: yun
 * @createdOn: 2019-07-16 10:42.
 */

@Component
@ConfigurationProperties(prefix = "ws")
@Data
public class WsProperties {
    private String wsHost;
    private String wsEndpoint = "skkjws";
}
