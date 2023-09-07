package com.mmzz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.common.CustomException;
import com.mmzz.domain.Setmeal;
import com.mmzz.domain.SetmealDish;
import com.mmzz.dto.SetmealDto;
import com.mmzz.mapper.SetmealMapper;
import com.mmzz.service.SetmealDishService;
import com.mmzz.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Resource
    private SetmealDishService setmealDishService;

    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map(setmealDish -> {
        setmealDish.setSetmealId(setmealDto.getId());
        return setmealDish;
        }).collect(Collectors.toList());

        //保存套餐的菜品信息关联信息
        setmealDishService.saveBatch(setmealDishes);

    }

    /**
     * 删除套餐
     * @param ids
     */
    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<Setmeal>();
        queryWrapper.in(Setmeal::getId, ids);
        //Status停售起售状态
        queryWrapper.eq(Setmeal::getStatus, 1);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        //如果可以删，先删除套餐表中的数据--setmeal
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<SetmealDish>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        //删除关系表中的数据SetmealDish
        setmealDishService.remove(setmealDishLambdaQueryWrapper);

    }

    @Override
    @Transactional
    public void updateWithDish(SetmealDto setmealDto) {
        //保存套餐基本信息
    this.updateById(setmealDto);

    List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
    setmealDishes.stream().map(setmealDish ->{
        setmealDish.setSetmealId(setmealDto.getId());
        return setmealDish;
    }).collect(Collectors.toList());

    //删除旧的关联信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<SetmealDish>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(queryWrapper);


        //保存套餐和菜品的关联信息
        setmealDishService.saveBatch(setmealDishes);
    }

}
