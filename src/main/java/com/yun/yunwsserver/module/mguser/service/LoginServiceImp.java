package com.yun.yunwsserver.module.mguser.service;

import com.yun.yunwsserver.module.BaseServiceImpl;
import com.yun.yunwsserver.module.mguser.dtovo.MgUserAccDto;
import com.yun.yunwsserver.module.mguser.dtovo.MgUserVo;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
import com.yun.yunwsserver.module.mguser.entity.MgUserJrp;
import com.yun.yunwsserver.module.mguser.entity.QMgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: yun
 * @createdOn: 2019-07-12 11:16.
 */

@Service
public class LoginServiceImp extends BaseServiceImpl {
    @Autowired
    private MgUserJrp mgUserJrp;

    @Transactional
    public void register(MgUserAccDto dto) {
        if (mgUserJrp.existsByInfo_AcctName(dto.getAcctName())) {
            throwCommonError("用户名已存在");
        }

        MgUser mgUser = MgUser.newUser(dto);
        mgUserJrp.save(mgUser);
    }

    @Transactional
    public MgUserVo login(MgUserAccDto dto) {
        QMgUser qMgUser = QMgUser.mgUser;

        MgUser mgUser = queryFactory.selectFrom(qMgUser)
                .where(qMgUser.password.eq(dto.getPassword())
                        .and(qMgUser.info.acctName.eq(dto.getAcctName())))
                .fetchFirst();

        if (mgUser == null) {
            throwCommonError("用户不存在");
        }

        return new MgUserVo(mgUser);
    }

    public MgUser checkTokenUser(String tokenStr, HttpServletRequest request) {
        QMgUser qUser = QMgUser.mgUser;

        MgUser appUser = queryFactory.selectFrom(qUser)
                .where(qUser.loginToken.eq(tokenStr))
                .fetchFirst();

        // token 无效
        if (appUser == null) {
            return null;
        }

        // 用户禁用了
        if (appUser.isInValidUser()) {
            throwCommonError("用户已禁用");
        }

        return appUser;
    }

    public MgUser checkAccUser(String accStr, HttpServletRequest request) {
        QMgUser qUser = QMgUser.mgUser;

        MgUser appUser = queryFactory.selectFrom(qUser)
                .where(qUser.accessKey.eq(accStr))
                .fetchFirst();

        // token 无效
        if (appUser == null) {
            return null;
        }

        // 用户禁用了
        if (appUser.isInValidUser()) {
            throwCommonError("用户已禁用");
        }

        return appUser;
    }
}
