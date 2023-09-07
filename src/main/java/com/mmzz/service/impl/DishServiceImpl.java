package com.mmzz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.domain.Dish;
import com.mmzz.domain.DishFlavor;
import com.mmzz.dto.DishDto;
import com.mmzz.mapper.DishMapper;
import com.mmzz.service.DishFlavorService;
import com.mmzz.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Resource
    private DishFlavorService dishFlavorService;

    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存本张表的信息
        this.save(dishDto);

        Long dishId=dishDto.getId();
        //保存本张表的口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors= flavors.stream().map((item) ->{
        item.setDishId(dishId);
        return item;
        }).collect(Collectors.toList());

        //保存菜品口味到菜品口味表中dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 回显数据到页面中
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //根据id查询菜品的基本信息
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        //复制将dish中的数据复制给dishDto中
        BeanUtils.copyProperties(dish,dishDto);

        LambdaQueryWrapper <DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表信息
        this.getById(dishDto);
        //清理当前菜品对应口味数据 dish_flavor标的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        List<DishFlavor> collect = flavors.stream().map(flavor -> {
            flavor.setDishId(dishDto.getId());
            return flavor;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(collect);

    }

    @Override
    public void deleteWithFlavor(String ids) {
        List<String> stringlist= Arrays.asList(ids.split(","));
        stringlist.forEach(item ->{
            long id = Long.parseLong(item);
            //删除菜品
            this.removeById(id);
            //删除口味
            LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,id);
            dishFlavorService.remove(queryWrapper);
        });

    }


}
