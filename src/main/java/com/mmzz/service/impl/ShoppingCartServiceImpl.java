package com.mmzz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.domain.ShoppingCart;
import com.mmzz.mapper.ShoppingCartMapper;
import com.mmzz.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
