package com.mmzz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.common.CustomException;
import com.mmzz.domain.Category;
import com.mmzz.domain.Dish;
import com.mmzz.domain.Setmeal;
import com.mmzz.mapper.CategoryMapper;
import com.mmzz.service.CategoryService;
import com.mmzz.service.DishService;
import com.mmzz.service.SetmealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private DishService dishService;

    @Resource
    private SetmealService setmealService;


    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count= (int) dishService.count(dishLambdaQueryWrapper);
        if(count>0){
            throw new CustomException("该分类下有菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1= (int) setmealService.count(setmealLambdaQueryWrapper);
        if(count1>0){
            throw new CustomException("该分类下有套餐，不能删除");
        }
        //正常删除操作
        super.removeById(id);

    }
}
