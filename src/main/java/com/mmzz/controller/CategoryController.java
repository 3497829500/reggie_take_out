package com.mmzz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmzz.common.R;
import com.mmzz.domain.Category;
import com.mmzz.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类管理
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询显示数据
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        //分页构造器
        Page <Category> pageinfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> lqw=new LambdaQueryWrapper<>();
        //添加排序条件
        lqw.orderByAsc(Category::getSort);
        //进行分页查询
        categoryService.page(pageinfo,lqw);

        return R.success(pageinfo);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("更新成功");

    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //设置查询条件
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //判断type是否为空，然后把type传进category中
        LambdaQueryWrapper<Category> eq = lambdaQueryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        //设置排序条件先根据sort，如果相同再根据updatetime排序
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return R.success(list);
    }





}
