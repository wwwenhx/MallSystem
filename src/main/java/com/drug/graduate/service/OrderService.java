package com.drug.graduate.service;

import com.drug.graduate.entity.Order;
import com.drug.graduate.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    //增加订单
    public Integer addOrder(Map<String, Object> param){
        return orderMapper.addOrder(param);
    }

    //支付后修改订单状态
    public Integer updateOrder(String orderId){
        return orderMapper.updateOrder(orderId);
    }

    //增加订单中商品信息
    public Integer addOrderGoods(Map<String, Object> param){
        return orderMapper.addOrderGoods(param);
    }

    //查询全部订单
    public List<Order> queryAllOrder(Integer userId){
        return orderMapper.queryAllOrder(userId);
    }

    //查询未完成订单
    public List<Order> queryOrderIncomplete(Integer userId){
        return orderMapper.queryOrderIncomplete(userId);
    }

    //查询未评价订单
    public List<Order> queryOrderEvaluate(Integer userId){
        return orderMapper.queryOrderEvaluate(userId);
    }

    //查询已完成订单
    public List<Order> queryOrderCompleted(Integer userId){
        return orderMapper.queryOrderCompleted(userId);
    }

    //查询未支付订单
    public List<Order> queryOrderUnpaid(Integer userId){
        return orderMapper.queryOrderUnpaid(userId);
    }

    //查询申请退款订单
    public List<Order> queryOrderRefund(Integer userId){
        return orderMapper.queryOrderRefund(userId);
    }


    //查询订单中的商品
    public List<Map<String, Object>> queryGoodsIdByOrder(String orderId, Integer userId){
        return orderMapper.queryGoodsIdByOrder(orderId,userId);
    }

    //查询订单状态
    public Integer queryOrderStatus(String orderId, Integer userId){
        return orderMapper.queryOrderStatus(orderId,userId);
    }

    //查询订单详情
    public Order queryOrderById(String orderId, Integer userId){
        return orderMapper.queryOrderById(orderId,userId);
    }

    //订单退款
    public Integer refundOrder(String orderId, Integer userId){ return orderMapper.refundOrder(orderId,userId);}

    //取消未支付订单
    public void cancelOrder(String orderId, Integer userId){
        orderMapper.cancelGoods(orderId, userId);
        orderMapper.cancelOrder(orderId, userId);
    }

    //确认收货
    public Integer receiveOrder(String orderId, Integer userId){
        return orderMapper.receiveOrder(orderId, userId);
    }

    //确认收货后订单中物品状态改为待评论
    public Integer commentOrder(String orderId, Integer userId){
        return orderMapper.commentOrder(orderId, userId);
    }

    //删除已完成订单
    public void deleteOrder(String orderId, Integer userId){
        orderMapper.deleteOrder(orderId, userId);
        orderMapper.deleteOrderGoods(orderId, userId);
    }

}
