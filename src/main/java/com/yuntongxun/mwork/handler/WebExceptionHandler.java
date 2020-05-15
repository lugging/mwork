package com.yuntongxun.mwork.handler;

import com.yuntongxun.mwork.flow.support.exception.BusinessException;
import com.yuntongxun.mwork.vo.support.Result;
import com.yuntongxun.mwork.vo.support.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: mwork
 * @description: exception
 * @author: liugang
 * @create: 2020-05-15 15:28
 **/
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.warn("BAD_REQUEST {}", e.getMessage());
        return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result handlerBusinessException(BusinessException e){
        log.warn("BusinessException code:{}", e.getCode());
        return ResultUtil.error(e.getCode());
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        log.error("Exception {} {}", e.getMessage(), e);
        return ResultUtil.error(500, e.getMessage());
    }
}
