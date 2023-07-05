package com.wzhy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Slf4j          //
@Configuration
public class WebMvcConfig  extends WebMvcConfigurationSupport {
//    静态资源访问设置
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
            log.info("开始静态资源映射");
            registry.addResourceHandler("/backend/**")
                    .addResourceLocations("classpath:/backend/");
            registry.addResourceHandler("/front/**")
                    .addResourceLocations("classpath:/front/");

            }
}
