package com.mmzz.dto;

import com.mmzz.domain.OrderDetail;
import com.mmzz.domain.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto extends Orders {
    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;

    private int sumNum;

}
