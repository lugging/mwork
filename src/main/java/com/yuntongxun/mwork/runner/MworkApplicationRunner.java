package com.yuntongxun.mwork.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @program: mwork
 * @description: start init
 * @author: liugang
 * @create: 2020-05-13 11:17
 **/
@Slf4j
@Component
public class MworkApplicationRunner implements ApplicationRunner {

    private final RegisterFlowHandler handler;

    @Autowired
    public MworkApplicationRunner(RegisterFlowHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("init start.");
        handler.init();
    }
}
