package com.mmzz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmzz.domain.Dish;
import com.mmzz.dto.DishDto;

public interface DishService extends IService<Dish> {

    //添加菜品需要2张表同时添加，dish,dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
     DishDto getByIdWithFlavor(Long id);

     //更新数据
     void updateWithFlavor(DishDto dishDto);

     //删除数据
    void deleteWithFlavor(String ids);
}
