package com.mmzz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmzz.common.R;
import com.mmzz.domain.Employee;
import com.mmzz.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登录
     */
    @PostMapping("/login")
    public R login(HttpServletRequest request, @RequestBody Employee employee){
        //进行md5加密处理
        String password=employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //查询数据库Username
        LambdaQueryWrapper<Employee> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,employee.getUsername());
        Employee emp= employeeService.getOne(lqw);

        //没有查询到则返回失败
        if(emp == null){
            return R.error("登录失败");
        }

        //密码对比
        if (!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }


        //查看员工状态
        if (emp.getStatus() ==0){
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        //设置初始密码
    employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

    //设置mybatis自动填充，无需手动填充
    //设置当前时间
      /*  employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());*/
        //获取当前登录用户的id
       /* Long l= (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(l);
        employee.setUpdateUser(l);*/

        employeeService.save(employee);
    return  R.success("新增成功");
    }

    /**
     * \分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
    log.info("page:{},pageSize:{},name:{}",page,pageSize,name);
    Page pageInfo=new Page(page,pageSize);
    LambdaQueryWrapper <Employee> queryWrapper=new LambdaQueryWrapper<>();
    queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
    queryWrapper.orderByDesc(Employee::getUpdateTime);
    employeeService.page(pageInfo,queryWrapper);

    return R.success(pageInfo);
    }

    /**
     * 修改员工状态信息
     * 获取id启动/禁用员工账号
     * @return
     */
    @PutMapping
    public R <String> update(HttpServletRequest request,@RequestBody Employee employee){
        Long employeeid = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(employeeid);
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);

        return R.success("修改员工状态成功");

    }

    /**
     *通过id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R <Employee> getById(@PathVariable Long id){
        Employee employee=employeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }else {
            return R.error("没有对应的员工信息");
        }




    }






}
