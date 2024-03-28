package com.drug.graduate.pay.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayBo {
    //省略其他的业务参数，如商品id、购买数量等
    //商品id

    //商品名称
    private String subject;

    //总金额
    private Double total;
}
