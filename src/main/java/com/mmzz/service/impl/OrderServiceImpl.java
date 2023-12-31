package com.mmzz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.common.BaseContext;
import com.mmzz.common.CustomException;
import com.mmzz.domain.*;
import com.mmzz.mapper.OrderMapper;
import com.mmzz.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private UserService userService;

    @Resource
    private AddressBookService addressBookService;

    @Resource
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param orders
     */
    @Transactional
    @Override
    public void submit(Orders orders) {
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();

        //查询当前用户购物车数据
        LambdaQueryWrapper <ShoppingCart> wrapper=new LambdaQueryWrapper();
        wrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);

        if (shoppingCarts == null && shoppingCarts.size() == 0){
            throw  new CustomException("购物车为空");
        }

        //查询用户数据
        User user = userService.getById(userId);

        //获取地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null){
            throw new CustomException("地址为空");
        }

        //订单号
        long id = IdWorker.getId();

        //原子操作类
        AtomicInteger amount=new AtomicInteger(0);

        //遍历购物车数据
        List<OrderDetail> orderDetails = shoppingCarts.stream().map(item -> {
            //订单明细
            OrderDetail ordersDetail = new OrderDetail();
            ordersDetail.setOrderId(id);
            ordersDetail.setNumber(item.getNumber());
            ordersDetail.setDishFlavor(item.getDishFlavor());
            ordersDetail.setDishId(item.getDishId());
            ordersDetail.setSetmealId(item.getSetmealId());
            ordersDetail.setName(item.getName());
            ordersDetail.setImage(item.getImage());
            ordersDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return ordersDetail;
        }).collect(Collectors.toList());

        //给orders赋值
        orders.setId(id);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(id));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);

        //向订单详情表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        //清空购物车
        shoppingCartService.remove(wrapper);


    }
}
