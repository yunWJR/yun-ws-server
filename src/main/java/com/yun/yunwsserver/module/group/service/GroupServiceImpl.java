package com.yun.yunwsserver.module.group.service;

import com.yun.base.Util.StringUtil;
import com.yun.yunwsserver.module.BaseServiceImpl;
import com.yun.yunwsserver.module.group.dtovo.GroupDto;
import com.yun.yunwsserver.module.group.dtovo.GroupVo;
import com.yun.yunwsserver.module.group.entity.*;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
import com.yun.yunwsserver.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yun
 * @createdOn: 2019-07-12 14:37.
 */

@Service
public class GroupServiceImpl extends BaseServiceImpl {
    @Autowired
    private GroupJrp groupJrp;

    @Autowired
    private GroupUserRlJrp userRlJrp;

    @Transactional
    public GroupVo creatGroup(GroupDto dto) {
        MgUser mgUser = RequestUtil.getAccessUser();

        Group group = getValidClientUser(dto.getClientGroupId(), false);
        if (group != null) {
            throwCommonError("群组已存在");

            return null;
        }

        group = Group.newItem(mgUser, dto);
        groupJrp.save(group);

        List<GroupUserRl> addUserList = new ArrayList<>();
        // 保存人员
        if (dto.getClientUserId() != null) {

            for (String cId : dto.getClientUserId()) {
                addUserList.add(new GroupUserRl(group, cId));
            }

            if (addUserList.size() > 0) {
                userRlJrp.saveAll(addUserList);
            }
        }

        GroupVo vo = new GroupVo(group, addUserList);

        return vo;
    }

    @Transactional
    public GroupVo groupInfo(String clientGroupId) {

        Group group = getValidClientUser(clientGroupId);

        QGroupUserRl qGu = QGroupUserRl.groupUserRl;
        List<GroupUserRl> rlList = queryFactory.selectFrom(qGu)
                .where(qGu.pkId.mgUserId.eq(group.getPkId().getMgUserId())
                        .and(qGu.pkId.clientGroupId.eq(group.getPkId().getClientGroupId())))
                .fetch();

        GroupVo vo = new GroupVo(group, rlList);

        return vo;
    }

    private Group getValidClientUser(String remarkId) {
        return getValidClientUser(remarkId, true);
    }

    private Group getValidClientUser(String remarkId, boolean throEp) {
        if (StringUtil.isNullOrEmpty(remarkId)) {
            if (throEp) {
                throwCommonError("参数错误");
            }

            return null;
        }

        MgUser mgUser = RequestUtil.getAccessUser();
        QGroup qGroup = QGroup.group;
        Group group = queryFactory.selectFrom(qGroup)
                .where(qGroup.pkId.mgUserId.eq(mgUser.getId())
                        .and(qGroup.pkId.clientGroupId.eq(remarkId)))
                .fetchFirst();

        if (group == null) {
            if (throEp) {
                throwCommonError("群组不存在");
            }

            return null;
        }

        return group;
    }
}
