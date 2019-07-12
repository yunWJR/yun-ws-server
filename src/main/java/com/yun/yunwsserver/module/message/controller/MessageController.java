package com.yun.yunwsserver.module.message.controller;

import com.yun.base.module.Bean.BaseRstBeanT;
import com.yun.yunwsserver.module.message.dtovo.MessageDto;
import com.yun.yunwsserver.module.message.service.MessageService;
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
 * @createdOn: 2019-07-12 16:54.
 */

@RestController
@RequestMapping("v1/api/message")
@Api(tags = "05-00-消息管理")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("pushMessage")
    @ApiOperation("pushMessage")
    public BaseRstBeanT<String> pushMessage(
            @RequestBody @Valid MessageDto dto) {
        messageService.pushMessage(dto);
        return BaseRstBeanT.SurBean("成功");

    }
}
