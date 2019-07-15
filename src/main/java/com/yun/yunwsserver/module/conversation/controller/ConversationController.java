package com.yun.yunwsserver.module.conversation.controller;

import com.yun.base.module.Bean.BaseRstBeanT;
import com.yun.yunwsserver.module.conversation.dtovo.ConversationDto;
import com.yun.yunwsserver.module.conversation.dtovo.ConversationVo;
import com.yun.yunwsserver.module.conversation.service.ConversationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author: yun
 * @createdOn: 2019-07-12 16:42.
 */

@RestController
@RequestMapping("v1/api/conversation")
@Api(tags = "04-00-对话管理")
public class ConversationController {

    @Autowired
    private ConversationServiceImpl groupService;

    @PostMapping("creat")
    @ApiOperation("creat")
    public BaseRstBeanT<ConversationVo> creat(
            @RequestBody @Valid ConversationDto dto) {
        return BaseRstBeanT.SurBean(groupService.creatGroup(dto));

    }
}
