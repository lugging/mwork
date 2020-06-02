package com.yuntongxun.mwork.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @program: mwork
 * @description: 工程自定义配置
 * @author: liugang
 * @create: 2020-05-25 11:33
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mwork")
public class MyConfigProperties {

    @NestedConfigurationProperty
    public AuthConfig authConfig = new AuthConfig();

    @NestedConfigurationProperty
    public Validation validation = new Validation();

    /**
     * 鉴权配置类
     */
    @Data
    public static class AuthConfig {
        /**
         * 是否启用
         */
        private boolean enabled = false;
        /**
         * 排除路径
         */
        private String[] excludePaths;
    }

    @Data
    public static class Validation {
        /**
         * 是否启用 完整的消息提示
         */
        private boolean showMessageEnabled = false;
    }
}
