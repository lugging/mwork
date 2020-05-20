package com.yuntongxun.mwork.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
* @Description:
 * 部署服务器系统： LocaleContextHolder.getLocale()
 * Request请求的语言:   RequestContextUtils.getLocale(request)
* @Author: liugang
* @Date: 2020/5/18 11:28
*/
@Component
public class MessageSourceHandler {

    private final HttpServletRequest request;

    private final MessageSource messageSource;

    @Autowired
    public MessageSourceHandler(HttpServletRequest request, MessageSource messageSource) {
        this.request = request;
        this.messageSource = messageSource;
    }

    public String getMessage(String messageKey) {
        String message = messageSource.getMessage(messageKey, null, RequestContextUtils.getLocale(request));
        return message;
    }
}