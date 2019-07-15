package com.yun.yunwsserver.module.conversation.service;

import com.yun.base.Util.StringUtil;
import com.yun.yunwsserver.module.BaseServiceImpl;
import com.yun.yunwsserver.module.conversation.dtovo.ConversationDto;
import com.yun.yunwsserver.module.conversation.dtovo.ConversationVo;
import com.yun.yunwsserver.module.conversation.entity.*;
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
public class ConversationServiceImpl extends BaseServiceImpl {
    @Autowired
    private ConversationJrp groupJrp;

    @Autowired
    private ConversationUserRlJrp userRlJrp;

    @Transactional
    public ConversationVo creatGroup(ConversationDto dto) {
        MgUser mgUser = RequestUtil.getAccessUser();

        Conversation conversation = getValidClientUser(dto.getClientGroupId(), false);
        if (conversation != null) {
            throwCommonError("群组已存在");

            return null;
        }

        conversation = Conversation.newItem(mgUser, dto);
        groupJrp.save(conversation);

        List<ConversationUserRl> addUserList = new ArrayList<>();
        // 保存人员
        if (dto.getClientUserId() != null) {

            for (String cId : dto.getClientUserId()) {
                addUserList.add(new ConversationUserRl(conversation, cId));
            }

            if (addUserList.size() > 0) {
                userRlJrp.saveAll(addUserList);
            }
        }

        ConversationVo vo = new ConversationVo(conversation, addUserList);

        return vo;
    }

    @Transactional
    public ConversationVo groupInfo(String clientGroupId) {

        Conversation conversation = getValidClientUser(clientGroupId);

        QGroupUserRl qGu = QGroupUserRl.groupUserRl;
        List<ConversationUserRl> rlList = queryFactory.selectFrom(qGu)
                .where(qGu.pkId.mgUserId.eq(conversation.getPkId().getMgUserId())
                        .and(qGu.pkId.clientGroupId.eq(conversation.getPkId().getClientGroupId())))
                .fetch();

        ConversationVo vo = new ConversationVo(conversation, rlList);

        return vo;
    }

    private Conversation getValidClientUser(String remarkId) {
        return getValidClientUser(remarkId, true);
    }

    private Conversation getValidClientUser(String remarkId, boolean throEp) {
        if (StringUtil.isNullOrEmpty(remarkId)) {
            if (throEp) {
                throwCommonError("参数错误");
            }

            return null;
        }

        MgUser mgUser = RequestUtil.getAccessUser();
        QGroup qGroup = QGroup.group;
        Conversation conversation = queryFactory.select(qGroup).from(qGroup)
                .where(qGroup.pkId.mgUserId.eq(mgUser.getId())
                        .and(qGroup.pkId.clientGroupId.eq(remarkId)))
                .fetchFirst();

        if (conversation == null) {
            if (throEp) {
                throwCommonError("群组不存在");
            }

            return null;
        }

        return conversation;
    }
}
