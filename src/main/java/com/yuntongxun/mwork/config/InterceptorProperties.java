package com.yuntongxun.mwork.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liugang
 * @Description 拦截器属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.interceptor")
public class InterceptorProperties {

    /**
     * 上传拦截器
     */
    @NestedConfigurationProperty
    private InterceptorConfig upload = new InterceptorConfig();

    @Data
    public static class InterceptorConfig {

        /**
         * 是否启用
         */
        private boolean enabled;

        /**
         * 排除路径
         */
        private String[] excludePaths;

        /**
         * 包含的路径
         */
        private String[] includePaths;

        /**
         * 允许上传下载的文件后缀集合
         */
        private List<String> allowFileExtensions;

    }
}