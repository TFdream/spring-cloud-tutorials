package io.dreamstudio.springcloud.resilience4j.web.advice;

import io.dreamstudio.springcloud.commons.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * @author Ricky Fung
 */
@ControllerAdvice
public class GlobalExceptionControllerAdvice {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    @ResponseBody
    public ResponseDTO exceptionHandler(MissingServletRequestParameterException e) {
        LOGGER.error("全局异常处理器-请求参数异常", e);
        return ResponseDTO.invalidParam("缺少参数");
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseDTO illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        LOGGER.error("全局处理器-非法请求参数, 原因:{}", ex.getMessage());
        return ResponseDTO.invalidParam(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseDTO exceptionHandler(Exception e) {
        LOGGER.error("全局异常处理器-未知异常", e);
        return ResponseDTO.systemError();
    }
}
