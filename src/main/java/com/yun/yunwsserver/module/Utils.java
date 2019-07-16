package com.yun.yunwsserver.module;

/**
 * @author: yun
 * @createdOn: 2019-07-16 10:28.
 */

public class Utils {
    public static String userSessionId(Long userId, Long mgId, String clientId) {
        String sId = userId + "-" + mgId + "-" + clientId;

        return sId;
    }
}
