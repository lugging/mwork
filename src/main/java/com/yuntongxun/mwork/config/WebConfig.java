package com.yuntongxun.mwork.config;

import com.yuntongxun.mwork.filter.AuthFilter;
import com.yuntongxun.mwork.interceptor.LocaleInterceptor;
import com.yuntongxun.mwork.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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

    private final MyConfigProperties myConfigProperties;

    @Autowired
    public WebConfig(MyConfigProperties myConfigProperties) {
        this.myConfigProperties = myConfigProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleInterceptor(myConfigProperties);
        localeChangeInterceptor.setParamName("Accept-Language");

        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/*");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        // 鉴权 Filter
        FilterRegistrationBean  bean = new FilterRegistrationBean(new AuthFilter(myConfigProperties));
        bean.addUrlPatterns("/*");
        return bean;
    }
}
