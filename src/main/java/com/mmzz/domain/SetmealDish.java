package com.mmzz.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 套餐菜品关系(SetmealDish)实体类
 *
 * @author makejava
 * @since 2023-06-08 15:34:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetmealDish implements Serializable {
private static final long serialVersionUID = 794740399014982311L;
/**主键*/private Long id;
/**套餐id */private Long  setmealId;
/**菜品id*/private Long  dishId;
/**菜品名称 （冗余字段）*/private String name;
/**菜品原价（冗余字段）*/private Double price;
/**份数*/private Integer copies;
/**排序*/private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;
}


