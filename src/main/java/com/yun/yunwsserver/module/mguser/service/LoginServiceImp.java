package com.yun.yunwsserver.module.mguser.service;

import com.yun.yunwsserver.module.BaseServiceImpl;
import com.yun.yunwsserver.module.mguser.dtovo.UserAccDto;
import com.yun.yunwsserver.module.mguser.dtovo.UserVO;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
import com.yun.yunwsserver.module.mguser.entity.MgUserJrp;
import com.yun.yunwsserver.module.mguser.entity.QMgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: yun
 * @createdOn: 2019-07-12 11:16.
 */

@Service
public class LoginServiceImp extends BaseServiceImpl {
    @Autowired
    private MgUserJrp mgUserJrp;

    @Transactional
    public void register(UserAccDto dto) {
        if (mgUserJrp.existsByInfo_AcctName(dto.getAcctName())) {
            throwCommonError("用户名已存在");
        }

        MgUser mgUser = MgUser.newUser(dto);
        mgUserJrp.save(mgUser);
    }

    @Transactional
    public UserVO login(UserAccDto dto) {
        QMgUser qMgUser = QMgUser.mgUser;

        MgUser mgUser = queryFactory.selectFrom(qMgUser)
                .where(qMgUser.password.eq(dto.getPassword())
                        .and(qMgUser.info.acctName.eq(dto.getAcctName())))
                .fetchFirst();

        if (mgUser == null) {
            throwCommonError("用户不存在");
        }

        return new UserVO(mgUser);
    }
}
