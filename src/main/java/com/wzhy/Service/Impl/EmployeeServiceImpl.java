package com.wzhy.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzhy.Service.EmployeeService;
import com.wzhy.entity.Employee;
import com.wzhy.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
//mbp规范
@Service
public class EmployeeServiceImpl  extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService
{

}
