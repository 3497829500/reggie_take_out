package com.mmzz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.domain.DishFlavor;
import com.mmzz.mapper.DishFlavorMapper;
import com.mmzz.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
