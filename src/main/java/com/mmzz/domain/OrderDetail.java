package com.mmzz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细表(OrderDetail)实体类
 *
 * @author makejava
 * @since 2023-06-08 15:34:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {
private static final long serialVersionUID = 100122153103212527L;
/**主键*/private Long id;
/**名字*/private String name;
/**图片*/private String image;
/**订单id*/private Long orderId;
/**菜品id*/private Long dishId;
/**套餐id*/private Long setmealId;
/**口味*/private String dishFlavor;
/**数量*/private Integer number;
/**金额*/private BigDecimal amount;
}


