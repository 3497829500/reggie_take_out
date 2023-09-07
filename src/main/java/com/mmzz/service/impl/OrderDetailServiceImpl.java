package com.mmzz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.domain.OrderDetail;
import com.mmzz.mapper.OrderDetailMapper;
import com.mmzz.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
