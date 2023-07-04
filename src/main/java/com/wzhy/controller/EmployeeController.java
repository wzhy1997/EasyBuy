package com.wzhy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wzhy.Service.EmployeeService;
import com.wzhy.common.R;
import com.wzhy.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
     @Autowired
    private EmployeeService employeeService;
//     因为是json对象所以用@Rqb
//    员工登录
    @RequestMapping("/login")
    private R<Employee> login(HttpServletRequest request, @RequestBody Employee employeeE){
//        1. 将页面提交的密码password进行md5加密处理
        String password = employeeE.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 2. 根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employeeE.getUsername());
        Employee one = employeeService.getOne(queryWrapper);
//        3. 如果没有查询到则返回登录失败结果
            if(one==null){
                return R.error("没有此用户");
            }
//        4. 密码比对，如果不一致则返回登录失败结果
        if(!one.getPassword().equals(password)){
            return R.error("密码错误");
        }
//        5. 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(one.getStatus()==0){
            return R.error("账号已经禁用");

        }
//        6. 登录成功，将员工id存入Session并返回登录成功结果
       request.getSession().setAttribute("employee",one.getId());
        return R.success(one);
    }
}
