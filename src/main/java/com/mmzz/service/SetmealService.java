package com.mmzz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmzz.domain.Setmeal;
import com.mmzz.dto.SetmealDto;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    //新增套餐2表新增
    void saveWithDish(SetmealDto setmealDto);

    //删除套餐
    void removeWithDish(List<Long> ids);

    //更新套餐
    void updateWithDish(SetmealDto setmealDto);


}
