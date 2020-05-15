package com.yuntongxun.mwork.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author liug
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.yuntongxun.mwork.repository.mapper"})
public class MybatisConfig {

    private static final String MYSQL_DATASOURCE = "DataSource";

    private static final String MAPPER_RESOURCES = "classpath:/spring/ds/mybatis/mappers/*Mapper.xml";

    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @ConfigurationProperties(prefix ="spring.datasource.hikari")
    @Bean(name = "hikariConfig")
    public HikariConfig hikariConfig(){
        return new HikariConfig();
    }

    @Bean(name = MYSQL_DATASOURCE)
    public DataSource dataSource(@Qualifier("hikariConfig") @Autowired HikariConfig config) {
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        return hikariDataSource;
    }

    /**
     * mysql mybatis SqlSessionFactory
     * @param dataSource
     * @return
     * @throws Exception
     *
     * maven 多模块项目的扫描路径需以 classpath*: 开头 （即加载多个 jar 包下的 xml 文件）
     */
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(MYSQL_DATASOURCE) DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_RESOURCES));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setPlugins(new Interceptor[]{
                // 此处添加各种拦截器插件
                paginationInterceptor()
        });
        return sqlSessionFactory.getObject();
    }

    /**
     *  sql 分页插件
     * @return
     */
    public Interceptor paginationInterceptor() {
        // 开启 count 的 join 优化,只针对 left join !!!
        return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
    }

    /**
     * mybatis plus 全局配置文件夹
     *
     * <href>https://mybatis.plus/config/#基本配置</href>
     *
     * @return
     */
    @Bean
    public GlobalConfig buildGlobalConfig(){
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        return globalConfig;
    }

}
