package com.mmzz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmzz.common.R;
import com.mmzz.domain.Category;
import com.mmzz.domain.Setmeal;
import com.mmzz.domain.SetmealDish;
import com.mmzz.dto.SetmealDto;
import com.mmzz.service.CategoryService;
import com.mmzz.service.DishService;
import com.mmzz.service.SetmealDishService;
import com.mmzz.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @Resource
    private SetmealDishService setmealDishService;

    @Resource
    private DishService dishService;

    @Resource
    private CategoryService categoryService;



    /**
     * 添加套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("保存成功");
    }

    /**
     * 分页查询，显示页面
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo=new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage=new Page<SetmealDto>();

        LambdaQueryWrapper <Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //查询，根据name进行like模糊查询
        queryWrapper.like(name!=null,Setmeal::getName,name);
        //排序降序排列
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝将pageInfo拷贝到dtoPage中
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> collect = records.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item, setmealDto);
            //获取id
            Long categoryId = item.getCategoryId();
            //根据id查询套餐详情
            Category byId = categoryService.getById(categoryId);
            if (byId != null) {
                //分类名称
                String categoryName = byId.getName();
                setmealDto.setCategoryName(categoryName);
            }

            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(collect);

        return R.success(dtoPage);
    }

    /**
     * 删除套餐功能
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除数据成功");
    }


    /**
     * 前端页面回显数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getDetail(@PathVariable String id){
        Setmeal byId = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        //拷贝数据
        BeanUtils.copyProperties(byId,setmealDto);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId,id);

        List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);
        setmealDto.setSetmealDishes(list);
        return R.success(setmealDto);
    }


    /**
     * 更新信息
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return R.success("更新套餐成功");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable Integer status, @RequestParam List<Long> ids){
        List<Setmeal> collect = ids.stream().map(item -> {
            Setmeal setmeal = new Setmeal();
            setmeal.setStatus(status);
            setmeal.setId(item);
            return setmeal;
        }).collect(Collectors.toList());
        setmealService.updateBatchById(collect);
        return R.success("修改状态成功");
    }



}
