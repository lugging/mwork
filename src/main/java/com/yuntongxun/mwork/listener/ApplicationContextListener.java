package com.yuntongxun.mwork.listener;

import com.yuntongxun.mwork.handler.RegisterFlowHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @program: mwork
 * @description:
 * @author: liugang
 * @create: 2020-05-23 11:10
 **/
@Slf4j
@Component
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(null == event.getApplicationContext().getParent()) {
            log.debug("spring初始化完毕");
            RegisterFlowHandler handler = event.getApplicationContext().getBean(RegisterFlowHandler.class);
            handler.init();
        }
    }
}
