package com.drug.graduate.entity;

import lombok.Data;

@Data
public class Address {
    private Integer id;
    private Integer userId;
    private String phoneNumber;
    private String name;
    private String province;
    private String city;
    private String county;
    private String detail;

}
