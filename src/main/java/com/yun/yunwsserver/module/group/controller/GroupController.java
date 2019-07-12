package com.yun.yunwsserver.module.group.controller;

import com.yun.base.module.Bean.BaseRstBeanT;
import com.yun.yunwsserver.module.group.dtovo.GroupDto;
import com.yun.yunwsserver.module.group.dtovo.GroupVo;
import com.yun.yunwsserver.module.group.service.GroupServiceImpl;
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
@RequestMapping("v1/api/group")
@Api(tags = "04-00-群组管理")
public class GroupController {

    @Autowired
    private GroupServiceImpl groupService;

    @PostMapping("creatGroup")
    @ApiOperation("creatGroup")
    public BaseRstBeanT<GroupVo> creatGroup(
            @RequestBody @Valid GroupDto dto) {
        return BaseRstBeanT.SurBean(groupService.creatGroup(dto));

    }
}
