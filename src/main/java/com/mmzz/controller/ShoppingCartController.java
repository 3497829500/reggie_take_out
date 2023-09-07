package com.mmzz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mmzz.common.BaseContext;
import com.mmzz.common.R;
import com.mmzz.domain.ShoppingCart;
import com.mmzz.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 *  购物车
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper=new LambdaQueryWrapper<ShoppingCart>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        lambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(lambdaQueryWrapper);

        return R.success(list);
    }

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        //获取id，指定当前哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper=new LambdaQueryWrapper<ShoppingCart>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);

        //判断添加的是菜品还是套餐
        if (dishId!=null){
            //添加到购物车是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId);
        }else {
            //添加到购物车的是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        //查询当前菜品或套餐是否已经在购物车中
        ShoppingCart one = shoppingCartService.getOne(lambdaQueryWrapper);
        if (one!=null){
            //如果已存在，就在原来数据量基础上加1
            Integer number = one.getNumber();
            one.setNumber(number+1);
            shoppingCartService.updateById(one);
        }else {
            //如果不存在，就添加到购物车，数量默认就是1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            one=shoppingCart;
        }

        return R.success(one);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //SQL:delete from shopping_cart where user_id = ?
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper=new LambdaQueryWrapper<ShoppingCart>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        shoppingCartService.remove(lambdaQueryWrapper);

        return R.success("清空成功");
    }


    /**
     * 数量-1
     * @param map
     * @return
     */
    @PostMapping("/sub")
    public R<String> sub(@RequestBody Map map){
    LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper=new LambdaQueryWrapper<ShoppingCart>();
    if (map.get("dishId")!=null){
        lambdaQueryWrapper.eq(ShoppingCart::getDishId,map.get("dishId"));
    }else {
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
    }
    lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
    ShoppingCart one=shoppingCartService.getOne(lambdaQueryWrapper);
    if (one.getNumber()>1){
        one.setNumber(one.getNumber()-1);
        shoppingCartService.updateById(one);
    }else {
        shoppingCartService.removeById(one);
    }
        return R.success("减数量成功");
    }

}
