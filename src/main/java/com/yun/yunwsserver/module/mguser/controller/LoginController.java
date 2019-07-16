package com.yun.yunwsserver.module.mguser.controller;

import com.yun.base.module.Bean.BaseRstBeanT;
import com.yun.yunwsserver.config.NoNeedAccessAuthentication;
import com.yun.yunwsserver.module.mguser.dtovo.MgUserAccDto;
import com.yun.yunwsserver.module.mguser.dtovo.MgUserVo;
import com.yun.yunwsserver.module.mguser.service.LoginServiceImp;
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
 * @createdOn: 2019-07-12 11:13.
 */

@RestController
@RequestMapping("v1/api/login")
@Api(tags = "01-00-登录管理")
public class LoginController {

    @Autowired
    private LoginServiceImp loginServiceImp;

    @PostMapping("register")
    @ApiOperation("注册")
    @NoNeedAccessAuthentication
    public BaseRstBeanT<String> register(
            @RequestBody @Valid MgUserAccDto dto
    ) {
        loginServiceImp.register(dto);
        return BaseRstBeanT.SurBean("注册成功");
    }

    @PostMapping("login")
    @ApiOperation("登录")
    @NoNeedAccessAuthentication
    public BaseRstBeanT<MgUserVo> login(
            @RequestBody @Valid MgUserAccDto dto
    ) {
        return BaseRstBeanT.SurBean(loginServiceImp.login(dto));
    }
}
