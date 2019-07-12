package com.yun.yunwsserver.config.exception;

import com.yun.base.module.Bean.BaseRstBean;
import com.yun.base.module.Bean.BaseRstCodeType;
import com.yun.base.module.Bean.RstBeanException;
import com.yun.base.token.AuthTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The itemType Global exception handler.
 * @author: yun
 * @createdOn: 2018 /6/6 14:16.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // region --Field

    // endregion

    // region --Constructor

    // endregion

    // region --static method

    // endregion   

    // region --Getter and Setter

    // endregion

    // region --Public method

    /**
     * 处理通用异常
     * @param e the e
     * @return the base rst bean
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    BaseRstBean handleException(Exception e) {
        String errMsg = this.getExceptionMsg(e);

        BaseRstBean rst = new BaseRstBean(BaseRstCodeType.InternalError, errMsg);
        return rst;
    }

    /**
     * 处理token 异常
     * @param e the e
     * @return the base rst bean
     */
    @ExceptionHandler(AuthTokenException.class)
    @ResponseBody
    BaseRstBean handleAuthTokenException(AuthTokenException e) {
        BaseRstBean rst = e.getRst();
        if (rst == null) {
            String errMsg = this.getExceptionMsg(e);
            rst = new BaseRstBean(BaseRstCodeType.TokenUnknown, errMsg);
        }

        return rst;
    }

    /**
     * Handle rst exception base rst bean.
     * @param e the e
     * @return the base rst bean
     */
    @ExceptionHandler(RstBeanException.class)
    @ResponseBody
    BaseRstBean handleRstException(RstBeanException e) {
        BaseRstBean rst = e.getRst();
        if (rst == null) {
            String errMsg = this.getExceptionMsg(e);
            rst = new BaseRstBean(BaseRstCodeType.TokenUnknown, errMsg);
        }

        return rst;
    }

    /**
     * 处理所有接口数据验证异常
     * @param e the e
     * @return the base rst bean
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    BaseRstBean handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errMsg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        BaseRstBean rst = new BaseRstBean(BaseRstCodeType.InValidData, errMsg);
        return rst;
    }

    // endregion

    // region --private method

    private String getExceptionMsg(Exception e) {
        String errMsg = "msg:" + e.getMessage() + "\n locMsg:" + e.getLocalizedMessage();

        log.error(errMsg);

        return errMsg;
    }

    // endregion

    // region --Other

    // endregion
}
