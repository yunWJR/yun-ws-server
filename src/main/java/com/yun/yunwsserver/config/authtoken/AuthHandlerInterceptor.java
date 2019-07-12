package com.yun.yunwsserver.config.authtoken;

import com.yun.base.Util.StringUtil;
import com.yun.base.module.Bean.RstBeanException;
import com.yun.yunwsserver.config.NoNeedAccessAuthentication;
import com.yun.yunwsserver.config.SetSystem;
import com.yun.yunwsserver.config.SpringContextUtil;
import com.yun.yunwsserver.config.SystemServiceImp;
import com.yun.yunwsserver.module.mguser.entity.MgUser;
import com.yun.yunwsserver.module.mguser.service.LoginServiceImp;
import com.yun.yunwsserver.util.GlobalConstant;
import com.yun.yunwsserver.util.ThreadLocalMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author: yun
 * @createdOn: 2019-07-12 17:06.
 */

@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    /**
     * 需要手动注入
     */
    private LoginServiceImp _loginServiceImp;

    /**
     * 该类不会自动注入，所以 bean 需要手动注入
     * @return
     */
    private LoginServiceImp loginServiceImp() {
        if (_loginServiceImp == null) {
            _loginServiceImp = SpringContextUtil.getBean(LoginServiceImp.class);
        }

        return _loginServiceImp;
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 跨域试探请求不处理 todo
        if (!(handler instanceof HandlerMethod)) {
            getDeviceType(request);
            return true;
        }

        if (!isSetSystem(handler) && !SystemServiceImp.isServerOn) {
            throw RstBeanException.RstComErrBeanWithStr("服务器已停止，请稍候重试");
        }

        // 根据注解确定是否检查
        if (noCheckToken(handler)) {
            getDeviceType(request);
            return true;
        }

        boolean hasAuth = false;

        String tokenStr = request.getHeader(GlobalConstant.Sys.TOKEN_AUTH_DTO);
        // 无 token
        if (!StringUtils.isBlank(tokenStr)) {
            MgUser appUser = loginServiceImp().checkTokenUser(tokenStr, request);

            // token 无效
            if (appUser != null) {
                hasAuth = true;
                ThreadLocalMap.put(GlobalConstant.Sys.TOKEN_AUTH_DTO, appUser);
            }
        }

        String accStr = request.getHeader(GlobalConstant.Sys.ACCESS_AUTH_DTO);
        // 无 token
        if (!StringUtils.isBlank(accStr)) {
            MgUser appUser = loginServiceImp().checkAccUser(accStr, request);

            // token 无效
            if (appUser != null) {
                hasAuth = true;
                ThreadLocalMap.put(GlobalConstant.Sys.ACCESS_AUTH_DTO, appUser);
            }
        }

        if (!hasAuth) {
            setRspNoToken(response);

            return false;
        }

        return true;
    }

    private void setRspNoToken(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Credentials", "false");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Expose-Headers", "Set-Cookie");
        response.setHeader("content-type", "application/json");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setStatus(401);
    }

    private void getDeviceType(HttpServletRequest request) {
        String tokenStr = request.getHeader("deviceType");
        if (!StringUtil.isNullOrEmpty(tokenStr)) {
            Integer dv = Integer.valueOf(tokenStr);

            if (dv != null) {
                ThreadLocalMap.put(GlobalConstant.Sys.DEVICE_TYPE_DTO, dv);
            }
        }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前(Controller方法调用之后)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalMap.remove();

        if (ex != null) {
            this.handleException(response);
        }
    }

    private void handleException(HttpServletResponse res) throws IOException {
        res.resetBuffer();
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write("{\"code\":100009 ,\"message\" :\"解析token失败\"}");
        res.flushBuffer();
    }

    private boolean noCheckToken(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method = handlerMethod.getMethod();

        NoNeedAccessAuthentication rps = AnnotationUtils.findAnnotation(method, NoNeedAccessAuthentication.class);

        return rps != null;
    }

    private boolean isSetSystem(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method = handlerMethod.getMethod();

        SetSystem rps = AnnotationUtils.findAnnotation(method, SetSystem.class);

        return rps != null;
    }
}

