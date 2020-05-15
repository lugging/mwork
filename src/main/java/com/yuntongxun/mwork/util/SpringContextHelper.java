package com.yuntongxun.mwork.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/** 
* @Description:
* @Param:  
* @return:  
* @Author: liugang
* @Date: 2020/5/13 11:05
*/
@Slf4j
@Component
public class SpringContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHelper.applicationContext = applicationContext;
        log.info(this.getClass().getName() + " init ..... ");
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class clazz) {
        checkApplicationContext();
        return (T) applicationContext.getBeansOfType(clazz);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * beanName, 构造方法
     */
    public static <T> T getBean(String name, Object ... objects) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name, objects);
    }

    public static <T extends Annotation> Map<String, Object> getBeansWithAnnotation(Class<T> clazz){
        checkApplicationContext();
        return applicationContext.getBeansWithAnnotation(clazz);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz){
        checkApplicationContext();
        return applicationContext.getBeansOfType(clazz);
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new NullPointerException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }
}