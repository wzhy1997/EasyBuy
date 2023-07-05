package com.wzhy.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

//全局管理有次注解的类
@ControllerAdvice(annotations ={RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    使用字符串拼接定位异常
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e){
        log.error(e.getMessage());
        if(e.getMessage().contains("Duplicate entry")){
//            引号有空格
            String[] split = e.getMessage().split(" ");
            String msg = split[2]+"已存在";
           return R.error(msg);

        }
        return R.error("未知错误");
    }
}

