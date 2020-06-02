package com.yuntongxun.mwork.handler;

import com.google.common.base.Joiner;
import com.yuntongxun.mwork.config.MyConfigProperties;
import com.yuntongxun.mwork.constant.CodeConstants;
import com.yuntongxun.mwork.filter.support.SignatureException;
import com.yuntongxun.mwork.flow.support.exception.BusinessException;
import com.yuntongxun.mwork.vo.support.Result;
import com.yuntongxun.mwork.vo.support.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: mwork
 * @description: exception
 * @author: liugang
 * @create: 2020-05-15 15:28
 **/
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {
    /**
     * 自定义配置文件
     */
    private final MyConfigProperties configProperties;

    /**
     * 引入国际化处理类
     */
    private MessageSourceHandler messageSourceHandler;

    @Autowired
    public WebExceptionHandler(MessageSourceHandler messageSourceHandler, MyConfigProperties configProperties) {
        this.messageSourceHandler = messageSourceHandler;
        this.configProperties = configProperties;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.warn("BAD_REQUEST {}", e.getMessage());
        return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handler(final MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        if (configProperties.getValidation().isShowMessageEnabled()){
            return ResultUtil.error(CodeConstants.CODE_400, msg);
        } else {
            return ResultUtil.error(CodeConstants.CODE_400, messageSourceHandler.getMessage(String.valueOf(CodeConstants.CODE_400)));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SignatureException.class)
    public Result handler(final SignatureException e) {
        if (configProperties.getValidation().isShowMessageEnabled()){
            return ResultUtil.error(CodeConstants.CODE_401, e.getMessage());
        } else {
            return ResultUtil.error(CodeConstants.CODE_401, messageSourceHandler.getMessage(String.valueOf(CodeConstants.CODE_401)));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public Result handlerValidationException(ValidationException e){
        log.warn("ValidationException message:{}", e.getMessage());
        String msg = "bad request, " + (!StringUtils.isEmpty(e.getMessage()) ?  e.getMessage() : "");
        if(e instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            msg = Joiner.on(",").join(violations.stream().map(constraintViolation -> constraintViolation.getMessage()).collect(Collectors.toList()));
        }
        if (configProperties.getValidation().isShowMessageEnabled()){
            return ResultUtil.error(CodeConstants.CODE_400, msg);
        } else {
            return ResultUtil.error(CodeConstants.CODE_400, messageSourceHandler.getMessage(String.valueOf(CodeConstants.CODE_400)));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public Result handlerBusinessException(BusinessException e){
        log.warn("BusinessException code:{}", e.getCode());
        return ResultUtil.error(e.getCode(), messageSourceHandler.getMessage(String.valueOf(e.getCode())));
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        log.error("Exception {} {}", e.getMessage(), e);
        return ResultUtil.error(CodeConstants.CODE_500, messageSourceHandler.getMessage(String.valueOf(CodeConstants.CODE_500)));
    }
}
