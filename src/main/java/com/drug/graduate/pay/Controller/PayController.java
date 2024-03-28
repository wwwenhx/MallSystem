package com.drug.graduate.pay.Controller;

import com.drug.graduate.pay.Service.PayService;
import com.drug.graduate.pay.bo.PayBo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/payService")
@AllArgsConstructor
public class PayController {
    @Autowired
    PayService payService;


    //下单支付
    @RequestMapping(value = "/alipay" , produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public Object pay (@RequestParam(required = false) PayBo bo, String price,String orderId,HttpServletResponse response) throws Exception {
        bo = new PayBo();
        double price2 = Double.parseDouble(price);
        bo.setTotal(price2);
        bo.setSubject("测试商品");
        System.out.println(bo);
        System.out.println(price);
        System.out.println(orderId);
        return payService.pay(bo,orderId);
    }

}
