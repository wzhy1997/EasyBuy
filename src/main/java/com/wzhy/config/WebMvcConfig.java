package com.wzhy.config;

import com.wzhy.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

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
//            扩展mvc的消息转换器 项目启动时会调用
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //mvc创建消息转换器
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//        设置对象转换器 底层用jackson 将java 转json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
//        将上面的消息转换器对象追加到mvc框架的转换器容器 并且设置优先级
        converters.add(0,messageConverter);
    }
}
