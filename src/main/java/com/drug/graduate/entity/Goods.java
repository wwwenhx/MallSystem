package com.drug.graduate.entity;


import lombok.Data;

@Data
public class Goods {
    private Integer id;
    private double price;
    private String name;
    private String synopsis;
    private String url;
    private String kind;
    private int sale;
    private int state;
    private String specifications;
    private Integer stock;

}