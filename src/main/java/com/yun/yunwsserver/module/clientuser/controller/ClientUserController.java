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
@Api(tags = "03-00-用户组管理")
public class ClientUserController {

    @Autowired
    private ClientUserServiceImpl clientUserService;

    @PostMapping("addClientUser")
    @ApiOperation("addClientUser")
    public BaseRstBeanT<ClientUserVo> addClientUser(
            @RequestBody @Valid ClientUserDto dto) {
        return BaseRstBeanT.SurBean(clientUserService.addClientUser(dto));

    }

    @GetMapping("clientUserInfo/{clientId}")
    @ApiOperation("clientUserInfo")
    public BaseRstBeanT<ClientUserVo> clientUserInfo(@PathVariable String clientId) {
        return BaseRstBeanT.SurBean(clientUserService.clientUserInfo(clientId));
    }

    @PostMapping("removeClientUser/{clientId}")
    @ApiOperation("removeClientUser")
    public BaseRstBeanT<String> removeClientUser(@PathVariable String clientId) {
        clientUserService.removeClientUser(clientId);
        return BaseRstBeanT.SurBean("成功");
    }

    @PostMapping("clientUserLogin/{clientId}/{platform}")
    @ApiOperation("clientUserLogin")
    public BaseRstBeanT<ClientUserLoginVo> clientUserLogin(
            @PathVariable String clientId,
            @PathVariable String platform) {
        return BaseRstBeanT.SurBean(clientUserService.clientUserLogin(clientId, platform));
    }
}
