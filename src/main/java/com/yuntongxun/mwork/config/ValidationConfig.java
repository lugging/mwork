package com.yuntongxun.mwork.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @program: mwork
 * @description: hibernate-validation
 * 提示信息配置:
 * @author: liugang
 * @create: 2020-05-18 09:35
 **/
@Configuration
public class ValidationConfig {

    private final MessageSource messageSource;

    @Autowired
    public ValidationConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 开启方法级别效验
     * @return
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator());
        return postProcessor;
    }

    /**
     * 设置快速失败 快速失败返回模式(只要有一个验证失败，则返回)
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        localValidatorFactoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
        return localValidatorFactoryBean;
    }
}
