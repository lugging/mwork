package com.yuntongxun.mwork.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

/**
 * @ClassName SwaggerConfig
 * @Description 文档展示
 * @Author liugang
 * @Date 2021/5/14 10:38
 * @Version
 */
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Autowired
    private Environment env;

    private String version;

    /**
     * 初始化获取当前版本version
     */
    @PostConstruct
    public void init(){
        version = env.getProperty("app.version");
    }

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现
     *
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("user")
                .select()
                //package包名替换为你的工程中需要扫描的接口的包名
                .apis(RequestHandlerSelectors.basePackage("com.yuntongxun.mwork.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("在线接口文档")
                .version(version)
                .build();
    }
}
