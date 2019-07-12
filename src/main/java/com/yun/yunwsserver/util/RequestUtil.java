package com.yun.yunwsserver.util;

import com.yun.base.module.Bean.BaseRstCodeType;
import com.yun.base.module.Bean.RstBeanException;
import com.yun.yunwsserver.module.mguser.entity.MgUser;

/**
 * @author: yun
 * @createdOn: 2019-07-02 15:36.
 */

public class RequestUtil {
    private static final String CONTROLLER_PARA = "CONTROLLER_PARA";

    public static void saveLoginUser(MgUser user) {
        ThreadLocalMap.put(GlobalConstant.Sys.TOKEN_AUTH_DTO, user);
    }

    public static MgUser getLoginUser() {
        MgUser loginUser = (MgUser) ThreadLocalMap.get(GlobalConstant.Sys.TOKEN_AUTH_DTO);
        if (loginUser == null) {
            throw RstBeanException.RstTypeErrBeanWithType(BaseRstCodeType.NoToken);
        }
        return loginUser;
    }

    public static void saveAccessUser(MgUser user) {
        ThreadLocalMap.put(GlobalConstant.Sys.ACCESS_AUTH_DTO, user);
    }

    public static MgUser getAccessUser() {
        MgUser loginUser = (MgUser) ThreadLocalMap.get(GlobalConstant.Sys.ACCESS_AUTH_DTO);
        if (loginUser == null) {
            throw RstBeanException.RstTypeErrBeanWithType(BaseRstCodeType.NoLimit);
        }
        return loginUser;
    }

    public static Long getLoginUserId() {
        return null;
    }

    public static String getControllerPara() {
        return null;
    }
}
