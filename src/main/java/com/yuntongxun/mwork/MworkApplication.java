package com.yuntongxun.mwork;

import com.alibaba.csp.sentinel.init.InitExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liugang
 */
@SpringBootApplication
public class MworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MworkApplication.class, args);
		InitExecutor.doInit();
	}
}
