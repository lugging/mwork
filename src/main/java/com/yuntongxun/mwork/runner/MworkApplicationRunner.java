package com.yuntongxun.mwork.runner;

import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void run(ApplicationArguments args) {
        log.info("init start.");
    }
}
