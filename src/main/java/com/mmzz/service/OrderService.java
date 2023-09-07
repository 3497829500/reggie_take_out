package com.mmzz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmzz.domain.Orders;

public interface OrderService extends IService<Orders> {

    //用户下单
    public void submit(Orders orders);
}
