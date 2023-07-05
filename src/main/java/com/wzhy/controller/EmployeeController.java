package com.wzhy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzhy.Service.EmployeeService;
import com.wzhy.common.R;
import com.wzhy.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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
//    账号退出功能
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
//    增员
    @PostMapping
//    为什么先调试 先看前端能否请求过
    public R<String> save(HttpServletRequest request,@RequestBody Employee e){
        log.info("新增员工，员工信息{}",e.toString());
//        设置初始密码 并密码加密
        e.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//       设置创建时间为系统时间
        e.setCreateTime(LocalDateTime.now());
        e.setUpdateTime(LocalDateTime.now());
//        获得当前用户id
        Long empId = (Long)request.getSession().getAttribute("employee");
        e.setCreateUser(empId);
        e.setUpdateUser(empId);
        employeeService.save(e);
        return R.success("新增员工成功");
//        username唯一约束 添加验证
    }
// 分页查询
//    name 是条件查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
//        构造分页器
        Page pageInfo = new Page(page,pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
//        添加过滤条件 apache 名字不为空时添加
        qw.like(StringUtils.isNotEmpty(name),Employee::getName,name);
//        排序 根据updateTime
        qw.orderByDesc(Employee::getUpdateTime);
//        执行查询
        employeeService.page(pageInfo,qw);
        return R.success(pageInfo);
    }
}
