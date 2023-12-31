package com.mmzz.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品管理(Dish)实体类
 *
 * @author makejava
 * @since 2023-06-08 15:34:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish implements Serializable {
private static final long serialVersionUID = 809002918519331262L;
/**主键*/private Long id;
/**菜品名称*/private String name;
/**菜品分类id*/private Long categoryId;
/**菜品价格*/private Double price;
/**商品码*/private String code;
/**图片*/private String image;
/**描述信息*/private String description;
/**0 停售 1 起售*/private Integer status;
/**顺序*/private Integer sort;

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


