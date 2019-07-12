package com.yun.yunwsserver.module.clientuser.service;

import com.yun.base.Util.StringUtil;
import com.yun.yunwsserver.module.BaseServiceImpl;
import com.yun.yunwsserver.module.clientuser.dtovo.ClientUserDto;
import com.yun.yunwsserver.module.clientuser.dtovo.ClientUserLoginVo;
import com.yun.yunwsserver.module.clientuser.dtovo.ClientUserVo;
import com.yun.yunwsserver.module.clientuser.entity.*;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
import com.yun.yunwsserver.module.wesocket.service.ImWebSocketService;
import com.yun.yunwsserver.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: yun
 * @createdOn: 2019-07-12 13:21.
 */

@Service
public class ClientUserServiceImpl extends BaseServiceImpl {
    @Autowired
    private ClientUserJrp clientUserJrp;

    @Autowired
    private ClientUserWsPlatformJrp platformJrp;

    @Autowired
    private ImWebSocketService imWebSocketService;

    @Transactional
    public ClientUserVo addClientUser(ClientUserDto dto) {
        MgUser mgUser = RequestUtil.getAccessUser();

        ClientUser newUser = getValidClientUser(dto.getClientUserId(), false);

        if (newUser != null) {
            return new ClientUserVo(newUser);
        }

        newUser = ClientUser.newUser(dto, mgUser);
        clientUserJrp.save(newUser);

        ClientUserVo vo = new ClientUserVo(newUser);

        return vo;
    }

    public ClientUserVo clientUserInfo(String clientId) {
        ClientUser cUser = getValidClientUser(clientId);

        ClientUserVo vo = new ClientUserVo(cUser);

        return vo;
    }

    @Transactional
    public void removeClientUser(String clientId) {
        MgUser mgUser = RequestUtil.getAccessUser();

        ClientUser newUser = getValidClientUser(clientId, false);
        if (newUser == null) {
            return;
        }

        // todo
        // 删除用户
    }

    @Transactional
    public ClientUserLoginVo clientUserLogin(String clientId, String platform) {
        ClientUser cUser = getValidClientUser(clientId);

        if (StringUtil.isNullOrEmpty(platform)) {
            platform = "0";
        }

        // 关闭当前链接
        // todo
        imWebSocketService.closeClient(null, platform);

        // 生成新链接
        ClientUserWsPlatform newPt = createNewPlatform(cUser, platform);

        return new ClientUserLoginVo(newPt);
    }

    private ClientUserWsPlatform createNewPlatform(ClientUser cUser, String platform) {
        QClientUserWsPlatform qPl = QClientUserWsPlatform.clientUserWsPlatform;
        ClientUserWsPlatform pt = queryFactory.selectFrom(qPl)
                .where(qPl.pkId.eq(cUser.getPkId())  // todo 测试
                        .and(qPl.platform.eq(platform)))
                .fetchFirst();

        if (pt == null) {
            pt = ClientUserWsPlatform.newItem(cUser, platform);
        }

        pt.renewPara();

        platformJrp.save(pt);

        return pt;
    }

    private ClientUser getValidClientUser(String clientId) {
        return getValidClientUser(clientId, true);
    }

    private ClientUser getValidClientUser(String clientId, boolean throEp) {
        if (StringUtil.isNullOrEmpty(clientId)) {
            if (throEp) {
                throwCommonError("参数错误");
            }

            return null;
        }

        MgUser mgUser = RequestUtil.getAccessUser();

        QClientUser qCU = QClientUser.clientUser;
        ClientUser cUser = queryFactory.selectFrom(qCU)
                .where(qCU.pkId.mgUserId.eq(mgUser.getId())
                        .and(qCU.pkId.clientUserId.eq(clientId)))
                .fetchFirst();

        if (cUser == null) {
            if (throEp) {
                throwCommonError("用户不存在");
            }

            return null;
        }

        return cUser;
    }

    public boolean isValidWsUser(String sessionId, String clientPt, String clientPara) {
        QClientUserWsPlatform qPlat = QClientUserWsPlatform.clientUserWsPlatform;

        QClientUser qCU = QClientUser.clientUser;
        ClientUser cUser = queryFactory.selectFrom(qCU)
                .where(qCU.sessionId.eq(sessionId))
                .innerJoin((qPlat))
                .on(qPlat.pkId.mgUserId.eq(qCU.pkId.mgUserId)
                        .and(qPlat.pkId.clientUserId.eq(qCU.pkId.clientUserId))
                        .and(qPlat.platform.eq(clientPt))
                        .and(qPlat.para.eq(clientPara)))
                .fetchFirst();

        return cUser != null;
    }
}
