package com.mmzz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.domain.Employee;
import com.mmzz.mapper.EmployeeMapper;
import com.mmzz.service.EmployeeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Resource
    EmployeeMapper mapper;

}
