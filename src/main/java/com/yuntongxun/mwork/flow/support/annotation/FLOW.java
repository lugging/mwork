package com.yuntongxun.mwork.flow.support.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/** 
* @Description: 流程执行注解
* @Param:  
* @return:  
* @Author: liugang
* @Date: 2020/5/13 10:35
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface FLOW {

    /**
     * 流程ID
     * @return
     */
    String flowId() default "";

    /**
     * 描述
     * @return
     */
    String description() default "";
}
