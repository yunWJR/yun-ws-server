package com.yun.yunwsserver.module.clientuser.controller;

import com.yun.base.module.Bean.BaseRstBeanT;
import com.yun.yunwsserver.module.clientuser.dtovo.ClientUserDto;
import com.yun.yunwsserver.module.clientuser.dtovo.ClientUserLoginVo;
import com.yun.yunwsserver.module.clientuser.dtovo.ClientUserVo;
import com.yun.yunwsserver.module.clientuser.service.ClientUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: yun
 * @createdOn: 2019-07-12 13:21.
 */

@RestController
@RequestMapping("v1/api/clientUser")
@Api(tags = "03-00-客户端用户管理")
public class ClientUserController {

    @Autowired
    private ClientUserServiceImpl clientUserService;

    @PostMapping("add")
    @ApiOperation("add")
    public BaseRstBeanT<ClientUserVo> addClientUser(
            @RequestBody @Valid ClientUserDto dto) {
        return BaseRstBeanT.SurBean(clientUserService.addClientUser(dto));
    }

    @GetMapping("info/{extraUserId}")
    @ApiOperation("info")
    public BaseRstBeanT<ClientUserVo> clientUserInfo(@PathVariable String extraUserId) {
        return BaseRstBeanT.SurBean(clientUserService.clientUserInfo(extraUserId));
    }

    @DeleteMapping("delete/{extraUserId}")
    @ApiOperation("delete")
    public BaseRstBeanT<String> removeClientUser(@PathVariable String extraUserId) {
        clientUserService.removeClientUser(extraUserId);
        return BaseRstBeanT.SurBean("成功");
    }

    @PostMapping("login/{extraUserId}/{platform}")
    @ApiOperation("login")
    public BaseRstBeanT<ClientUserLoginVo> clientUserLogin(
            @PathVariable String extraUserId,
            @PathVariable String platform) {
        return BaseRstBeanT.SurBean(clientUserService.clientUserLogin(extraUserId, platform));
    }
}
