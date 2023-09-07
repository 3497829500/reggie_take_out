package com.mmzz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmzz.common.BaseContext;
import com.mmzz.common.R;
import com.mmzz.domain.OrderDetail;
import com.mmzz.domain.Orders;
import com.mmzz.dto.OrderDto;
import com.mmzz.service.OrderDetailService;
import com.mmzz.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单表 前端控制器
 */
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderDetailService orderDetailService;

    /**
     * 购物车下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 分页查询显示订单
     */
    @GetMapping("/userPage")
    public R<Page<OrderDto>> userPage(int page,int pageSize){
        Page<Orders> ordersPage=new Page<Orders>(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());

        Page<Orders> ordersPage1= orderService.page(ordersPage,queryWrapper);
        List<Orders> records = ordersPage1.getRecords();
        Page<OrderDto> dtoPage=new Page<OrderDto>();
        //复制拷贝
        BeanUtils.copyProperties(ordersPage1,dtoPage,"records");

        List<OrderDto> collect = records.stream().map(item -> {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(item, orderDto);
            LambdaQueryWrapper<OrderDetail> orderDetailQueryWrapper = new LambdaQueryWrapper<>();
            orderDetailQueryWrapper.eq(OrderDetail::getOrderId, item.getId());
            List<OrderDetail> orderDetailList = orderDetailService.list(orderDetailQueryWrapper);
            orderDto.setOrderDetails(orderDetailList);
            orderDto.setSumNum(orderDetailList.size());
            return orderDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(collect);
        return R.success(dtoPage);
    }

}
