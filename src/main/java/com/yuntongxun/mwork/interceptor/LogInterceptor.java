package com.yuntongxun.mwork.interceptor;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: mwork
 * @description: log
 * @author: liugang
 * @create: 2020-05-13 13:47
 **/
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private static final String LOG_KEY = "TRACER_ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tracerId = request.getHeader(LOG_KEY);
        if (ObjectUtils.isEmpty(tracerId)){
            tracerId = IdWorker.getIdStr();
        }
        if ( !response.containsHeader(LOG_KEY) ){
            response.setHeader(LOG_KEY, MDC.get(LOG_KEY));
        }
        MDC.put(LOG_KEY, tracerId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(LOG_KEY);
    }
}
