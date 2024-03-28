package com.drug.graduate.controller;

import com.alipay.api.kms.aliyun.http.HttpResponse;
import com.drug.graduate.entity.Address;
import com.drug.graduate.entity.Goods;
import com.drug.graduate.entity.Message;
import com.drug.graduate.entity.Order;
import com.drug.graduate.pay.util.OrderUtil;
import com.drug.graduate.service.GoodsService;
import com.drug.graduate.service.OrderService;
import com.drug.graduate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/orderService")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    //支付宝支付成功回调方法
    @RequestMapping("/addOrder")
    @ResponseBody
    public void addOrder(HttpServletRequest request){
        System.out.println("进入回调");
        Map map = request.getParameterMap();
        String[] orderIdStr = (String[]) map.get("out_trade_no");
        String orderId = orderIdStr[0];
        orderService.updateOrder(orderId);

    }


    //添加未支付订单
    @RequestMapping("/addUnprocessedOrder")
    @ResponseBody
    public Message addUnprocessedOrder(@CookieValue("userId") Integer userId, String address, @CookieValue("goodsIdList") String[] goodsIdList, @CookieValue("numList") String[] num, Double price){
        Message message = new Message();
        String orderId = OrderUtil.getOrderNo();
        Map<String, Object> param = new HashMap<>();
        param.put("id",orderId);
        param.put("userId",userId);
        param.put("price",price);
        param.put("address",address);
        param.put("status",3);
        orderService.addOrder(param);
        for(int i = 0; i < goodsIdList.length; i++){
            Map<String, Object> param2 = new HashMap<>();
            param2.put("userId",userId);
            param2.put("orderId",orderId);
            param2.put("goodsId",goodsIdList[i]);
            param2.put("goodsNum",num[i]);
            orderService.addOrderGoods(param2);
            //添加成功后将药品从购物车中删除
            goodsService.shopOrderGoods(Integer.parseInt(goodsIdList[i]),userId);
            //订单后减少库存
            goodsService.updateStock(param2);
        }
        message.setBody(orderId);
        return message;
    }

    //查询全部订单
    @RequestMapping("/queryAllOrder")
    @ResponseBody
    public Message queryAllOrder(Integer userId){
        Message message = new Message();
        List<Order> orderList = orderService.queryAllOrder(userId);
        if(orderList != null && orderList.size()>0){
            message.setCode(200);
            message.setBody(orderList);
            return message;
        }
        message.setCode(400);
        message.setMsg("暂无订单");
        return message;
    }

    //查询未完成订单
    @RequestMapping("/queryOrderIncomplete")
    @ResponseBody
    public Message queryOrderIncomplete(Integer userId){
        Message message = new Message();
        List<Order> orderList = orderService.queryOrderIncomplete(userId);
        if(orderList != null){
            message.setCode(200);
            message.setBody(orderList);
            return message;
        }
        message.setCode(400);
        message.setMsg("暂无订单");
        return message;
    }

    //获取未评价药品数据
    @RequestMapping("/queryOrderEvaluate")
    @ResponseBody
    public Message queryOrderEvaluate(Integer userId){
        Message message = new Message();
        List<Map<String, Object>> goodsList = goodsService.getEvaluateGoods(userId);
        if(goodsList != null){
            message.setCode(200);
            message.setBody(goodsList);
            return message;
        }
        message.setCode(400);
        message.setMsg("暂无订单");
        return message;
    };

    //查询已完成订单
    @RequestMapping("/queryOrderCompleted")
    @ResponseBody
    public Message queryOrderCompleted(Integer userId){
        Message message = new Message();
        List<Order> orderList = orderService.queryOrderCompleted(userId);
        if(orderList != null){
            message.setCode(200);
            message.setBody(orderList);
            return message;
        }
        message.setCode(400);
        message.setMsg("暂无订单");
        return message;
    }

    //获取未支付订单页
    @RequestMapping("/queryOrderUnpaid")
    @ResponseBody
    public Message queryOrderUnpaid(Integer userId){
        Message message = new Message();
        List<Order> orderList = orderService.queryOrderUnpaid(userId);
        if(orderList != null){
            message.setCode(200);
            message.setBody(orderList);
            return message;
        }
        message.setCode(400);
        message.setMsg("暂无订单");
        return message;
    };

    //获取退款订单页
    @RequestMapping("/queryOrderRefund")
    @ResponseBody
    public Message queryOrderRefund(Integer userId){
        Message message = new Message();
        List<Order> orderList = orderService.queryOrderRefund(userId);
        if(orderList != null){
            message.setCode(200);
            message.setBody(orderList);
            return message;
        }
        message.setCode(400);
        message.setMsg("暂无订单");
        return message;
    };

    //药品详情页面
    @RequestMapping("/orderDetail")
    public String toOrderDetail(@CookieValue("userId") Integer userId, String orderId, String price, Model model){
        Order order = orderService.queryOrderById(orderId,userId);
        int status = order.getStatus();
        if(status == 0){
            model.addAttribute("status","未发货");
        }
        else if (status == 1){
            model.addAttribute("status","已发货");
        }
        else if(status == 2){
            model.addAttribute("status","已完成");
        }
        else if (status == 3){
            model.addAttribute("status","未支付");

        }
        else if (status == 4){
            model.addAttribute("status","申请退款");

        }
        else if (status == 5){
            model.addAttribute("status","已退款");

        }
        else if (status == 6){
            model.addAttribute("status","待评价");

        }
        model.addAttribute("orderId",orderId);
        model.addAttribute("price", order.getPrice());
        model.addAttribute("userId", userId);
        return "user/OrderDetail";
    }


    //订单详情药品数据
    @RequestMapping("/orderGoodsData")
    @ResponseBody
    public Map<String, Object> getOrderGoodsData(Integer userId,String orderId){
        Map<String, Object> param = new HashMap<>();
        List<Map<String, Object>> list = orderService.queryGoodsIdByOrder(orderId,userId);
        List<Integer> goodsIdList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        if(list !=null ){
            for(int i = 0; i < list.size(); i++){
                goodsIdList.add((Integer) list.get(i).get("goodsId"));
                numList.add((Integer) list.get(i).get("goodsNum"));
            }
        }

        List<Goods> goods = new ArrayList<>();
        if(goodsIdList != null){
            for(int i = 0; i < goodsIdList.size(); i ++){
                Integer goodsId = goodsIdList.get(i);
                goods.add(goodsService.getGoodsDetails(goodsId));
            }
        }

        param.put("goods",goods);
        param.put("num",numList);
        return param;
    }

    //订单申请退款
    @RequestMapping("/refundOrder")
    @ResponseBody
    public Message refundOrder(@CookieValue("userId") Integer userId, String orderId){
        Message message = new Message();
        orderService.refundOrder(orderId, userId);
        message.setBody("申请成功");
        return message;
    }

    //取消订单
    @RequestMapping("/cancelOrder")
    @ResponseBody
    public Message cancelOrder(@CookieValue("userId") Integer userId, String orderId){
        Message message = new Message();
        orderService.cancelOrder(orderId, userId);
        message.setBody("取消成功");
        return message;
    }

    //确认收货
    @RequestMapping("/receiveOrder")
    @ResponseBody
    public Message receiveOrder(Integer userId, String orderId){
        Message message = new Message();
        orderService.receiveOrder(orderId , userId);
        orderService.commentOrder(orderId, userId);
        List<Map<String, Object>> goodsList = orderService.queryGoodsIdByOrder(orderId,userId);
        if(goodsList != null && goodsList.size() > 0){
            for(int i = 0; i < goodsList.size(); i++){
                Map<String, Object> param = new HashMap<>();
                param.put("num",goodsList.get(i).get("goodsNum"));
                param.put("goodsId",goodsList.get(i).get("goodsId"));
                goodsService.addSale(param);
            }
        }
        message.setCode(200);
        return message;
    }

    //删除已完成订单
    @RequestMapping("/deleteOrder")
    @ResponseBody
    public Message deleteOrder(@CookieValue("userId") Integer userId, String orderId){
        Message message = new Message();
        orderService.deleteOrder(orderId, userId);
        message.setBody("删除成功");
        return message;
    }
}
