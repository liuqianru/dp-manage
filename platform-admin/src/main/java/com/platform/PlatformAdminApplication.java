package com.platform;

import com.platform.datasources.DynamicDataSourceConfig;
import com.platform.modules.cache.CacheFactory;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author 李鹏军
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class})
@Import({DynamicDataSourceConfig.class})
@EnableAsync
@ServletComponentScan
public class PlatformAdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PlatformAdminApplication.class, args);
        CacheFactory.CacheFlow("dept");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PlatformAdminApplication.class);
    }
}
