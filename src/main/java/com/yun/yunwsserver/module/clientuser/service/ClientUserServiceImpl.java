package com.yun.yunwsserver.module.clientuser.service;

import com.yun.base.Util.StringUtil;
import com.yun.yunwsserver.config.WsProperties;
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

    @Autowired
    private WsProperties wsProperties;

    @Transactional
    public ClientUserVo addClientUser(ClientUserDto dto) {
        MgUser mgUser = RequestUtil.getAccessUser();

        ClientUser newUser = getValidClientUser(dto.getExtraUserId(), false);

        if (newUser != null) {
            return new ClientUserVo(newUser);
        }

        newUser = ClientUser.newUser(dto, mgUser);
        clientUserJrp.save(newUser);

        newUser.resetSsId();
        clientUserJrp.save(newUser);

        ClientUserVo vo = new ClientUserVo(newUser);

        return vo;
    }

    public ClientUserVo clientUserInfo(String extraUserId) {
        ClientUser cUser = getValidClientUser(extraUserId);

        ClientUserVo vo = new ClientUserVo(cUser);

        return vo;
    }

    @Transactional
    public void removeClientUser(String extraUserId) {
        MgUser mgUser = RequestUtil.getAccessUser();

        ClientUser newUser = getValidClientUser(extraUserId, false);
        if (newUser == null) {
            return;
        }

        // todo
        // 删除用户
    }

    @Transactional
    public ClientUserLoginVo clientUserLogin(String extraUserId, String platform) {
        ClientUser cUser = getValidClientUser(extraUserId);

        if (StringUtil.isNullOrEmpty(platform)) {
            platform = "0";
        }

        // 关闭当前链接
        // todo
        imWebSocketService.closeClient(cUser.getSessionId(), platform);

        // 生成新链接
        ClientUserWsPlatform newPt = createNewPlatform(cUser, platform);

        ClientUserLoginVo vo = new ClientUserLoginVo(cUser, newPt);

        vo.creatPath(wsProperties.getWsHost(), wsProperties.getWsEndpoint());

        return vo;
    }

    public boolean isValidWsUser(String sessionId, String clientPt, String clientPara) {
        QClientUserWsPlatform qPlat = QClientUserWsPlatform.clientUserWsPlatform;

        QClientUser qCu = QClientUser.clientUser;
        ClientUser cUser = queryFactory.selectFrom(qCu)
                .where(qCu.sessionId.eq(sessionId))
                .innerJoin((qPlat))
                .on(qPlat.clientUserId.eq(qCu.id)
                        .and(qPlat.platform.eq(clientPt))
                        .and(qPlat.para.eq(clientPara)))
                .fetchFirst();

        return cUser != null;
    }

    // region private function

    private ClientUserWsPlatform createNewPlatform(ClientUser cUser, String platform) {
        QClientUserWsPlatform qPt = QClientUserWsPlatform.clientUserWsPlatform;
        ClientUserWsPlatform pt = queryFactory.selectFrom(qPt)
                .where(qPt.clientUserId.eq(cUser.getId())
                        .and(qPt.platform.eq(platform)))
                .fetchFirst();

        if (pt == null) {
            pt = ClientUserWsPlatform.newItem(cUser, platform);
        }

        pt.renewPara();

        platformJrp.save(pt);

        return pt;
    }

    private ClientUser getValidClientUser(String extraUserId) {
        return getValidClientUser(extraUserId, true);
    }

    private ClientUser getValidClientUser(String extraUserId, boolean throEp) {
        if (StringUtil.isNullOrEmpty(extraUserId)) {
            if (throEp) {
                throwCommonError("参数错误");
            }

            return null;
        }

        MgUser mgUser = RequestUtil.getAccessUser();

        QClientUser qCu = QClientUser.clientUser;
        ClientUser cUser = queryFactory.selectFrom(qCu)
                .where(qCu.mgUserId.eq(mgUser.getId())
                        .and(qCu.extraUserId.eq(extraUserId)))
                .fetchFirst();

        if (cUser == null) {
            if (throEp) {
                throwCommonError("用户不存在");
            }

            return null;
        }

        return cUser;
    }

    // endregion
}
