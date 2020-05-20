package com.yuntongxun.mwork.config;

import com.yuntongxun.mwork.interceptor.LocaleInterceptor;
import com.yuntongxun.mwork.interceptor.LogInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @program: mwork
 * @description: config
 * @author: liugang
 * @create: 2020-05-13 15:08
 **/
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleInterceptor();
        localeChangeInterceptor.setParamName("Accept-Language");

        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/*");
        registry.addInterceptor(localeChangeInterceptor);
    }
}
