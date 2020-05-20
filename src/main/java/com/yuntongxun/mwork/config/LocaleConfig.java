package com.yuntongxun.mwork.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @program: mwork
 * @description:  Hibernate validation 国际化配置
 *
 * ``` 获取到国际化资源
 *   String message = messageSource.getMessage(messageKey, null, RequestContextUtils.getLocale(request));
 * ```
 *
 *
 * @author: liugang
 * @create: 2020-05-18 11:08
 **/
@Configuration
public class LocaleConfig {

    @Value(value = "${spring.messages.basename}")
    private String basename;

    /**
     *  初始化国际化资源
     * @return
     */
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasename(basename);
        return messageSource;
    }

    /**
     * 解析器 其中locale表示默认语言
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return localeResolver;
    }
}
