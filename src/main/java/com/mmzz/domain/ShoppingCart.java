package com.mmzz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车(ShoppingCart)实体类
 *
 * @author makejava
 * @since 2023-06-08 15:34:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart implements Serializable {
private static final long serialVersionUID = -74948686319171369L;
/**主键*/private Long id;
/**名称*/private String name;
/**图片*/private String image;
/**主键*/private Long userId;
/**菜品id*/private Long dishId;
/**套餐id*/private Long setmealId;
/**口味*/private String dishFlavor;
/**数量*/private Integer number;
/**金额*/private BigDecimal amount;
/**创建时间*/private LocalDateTime createTime;
}


