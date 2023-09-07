package com.mmzz.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品及套餐分类(Category)实体类
 *
 * @author makejava
 * @since 2023-06-08 15:34:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
private static final long serialVersionUID = -48132613130638676L;
/**主键*/private Long id;
/**类型   1 菜品分类 2 套餐分类*/private Integer type;
/**分类名称*/private String name;
/**顺序*/private Integer sort;
    @TableField(fill = FieldFill.INSERT)
/**创建时间*/private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
/**更新时间*/private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
/**创建人*/private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
/**修改人*/private Long updateUser;
}


