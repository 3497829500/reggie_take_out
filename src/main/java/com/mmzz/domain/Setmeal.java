package com.mmzz.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 套餐(Setmeal)实体类
 *
 * @author makejava
 * @since 2023-06-08 15:34:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setmeal implements Serializable {
private static final long serialVersionUID = 812609372595062491L;
/**主键*/private Long id;
/**菜品分类id*/private Long categoryId;
/**套餐名称*/private String name;
/**套餐价格*/private Double price;
/**状态 0:停用 1:启用*/private Integer status;
/**编码*/private String code;
/**描述信息*/private String description;
/**图片*/private String image;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

/**是否删除*/private Integer isDeleted;
}


