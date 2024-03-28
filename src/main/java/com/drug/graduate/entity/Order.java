package com.drug.graduate.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private String id;
    private Integer userId;
    private String createTime;
    private Double price;
    private  String address;
    private Integer status;
    private Integer orderType;

}
