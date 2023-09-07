package com.mmzz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmzz.common.R;
import com.mmzz.domain.Category;
import com.mmzz.domain.Dish;
import com.mmzz.domain.DishFlavor;
import com.mmzz.dto.DishDto;
import com.mmzz.service.CategoryService;
import com.mmzz.service.DishFlavorService;
import com.mmzz.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private DishService dishService;

    @Resource
    private DishFlavorService dishFlavorService;


    @Resource
    private CategoryService categoryService;


    /**
     * 添加数据
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        //添加数据
        dishService.saveWithFlavor(dishDto);
        return R.success("添加菜品成功");
    }


    /**
     * 分页查询数据
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        Page<Dish> pageinfo=new Page<>(page,pageSize);
        Page<DishDto> pageDto=new Page<>();

        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加过滤条件
        lambdaQueryWrapper.like(name!=null,Dish::getName,name);
        //添加排序条件
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pageinfo,lambdaQueryWrapper);

        //return传给前端pageinfo,查询一张表，前端菜品分类没有值
        /*return R.success(pageinfo);*/

        //使用dto
        //对象拷贝，将pageinfo拷贝到pageDto,不包含records
        BeanUtils.copyProperties(pageinfo,pageDto,"records");
        //获取查询的数据List<Dish>
        List<Dish> records = pageinfo.getRecords();

        //使用stream流
        List<DishDto> dishDtoList=records.stream().map((item) -> {
            DishDto dishDto=new DishDto();
            //拷贝item,dishDto
            BeanUtils.copyProperties(item,dishDto);
            //获取分类id
            Long categoryId = item.getCategoryId();

            Category category = categoryService.getById(categoryId);
            //获取name
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);

            return dishDto;
        }).collect(Collectors.toList());;

        //将获取的数据传入pageDto中的Records中
        pageDto.setRecords(dishDtoList);

        //最后返回包含菜品分类的所有数据
        return R.success(pageDto);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);
        return R.success(byIdWithFlavor);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @DeleteMapping
    public R<String> delete(String ids){
        dishService.deleteWithFlavor(ids);
    return R.success("删除菜品成功");
    }

    /**
     * 根据条件查询对应的菜品数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> collect = list.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();

            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String name = category.getName();
                dishDto.setCategoryName(name);
            }
            //当前菜品的id
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper= new LambdaQueryWrapper<DishFlavor>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,id);
            List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(collect);
    }

}
