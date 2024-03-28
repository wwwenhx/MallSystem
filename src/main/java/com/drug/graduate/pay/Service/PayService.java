package com.drug.graduate.pay.Service;

import com.drug.graduate.pay.bo.PayBo;
import com.drug.graduate.pay.util.OrderUtil;
import org.springframework.stereotype.Service;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayService {
    //支付成功后要跳转的页面
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    /**
     *  下单支付
     * */
    public Object pay (PayBo bo,String orderId) throws Exception {
        //调用sdk，发起支付
        AlipayTradePagePayResponse response = Factory.Payment
                //选择网页支付平台
                .Page()
                //调用支付方法，设置订单名称、我们自己系统中的订单号、金额、回调页面
                .pay(bo.getSubject() , orderId, bo.getTotal().toString() , returnUrl);


        //这里的response.body，就是一个可以直接加载的html片段，
        // 这里为了简单我直接返回这个片段，前端直接
        return response.body;
    }
}
