package com.wzhy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
//加上才寻找filter
@ServletComponentScan
public class EasyBuyApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyBuyApplication.class,args);
        log.info("项目启动成功");
    }
}
