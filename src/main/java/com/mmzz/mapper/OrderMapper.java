package com.mmzz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mmzz.domain.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}