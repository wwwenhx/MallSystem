package com.drug.graduate.entity;

import lombok.Data;

@Data
public class OrderGoods {
    private Integer userId;
    private String orderId;
    private Integer goodsId;
    private Integer num;
    private Integer status;
}
