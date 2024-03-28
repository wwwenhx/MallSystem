package com.drug.graduate.mapper;

import com.drug.graduate.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    //增加订单
    public Integer addOrder(Map<String, Object> param);

    //增加订单中商品信息
    public Integer addOrderGoods(Map<String, Object> param);

    //查询全部订单
    public List<Order> queryAllOrder(Integer userId);

    //查询未完成订单
    public List<Order> queryOrderIncomplete(Integer userId);

    //查询待评价订单
    public List<Order> queryOrderEvaluate(Integer userId);

    //查询已完成的订单
    public List<Order> queryOrderCompleted(Integer userId);

    //查询未支付的订单
    public List<Order> queryOrderUnpaid(Integer userId);

    //查询申请退款的订单
    public List<Order> queryOrderRefund(Integer userId);

    //支付订单修改订单状态
    public Integer updateOrder(String orderId);

    //查询订单中的商品id
    public List<Map<String, Object>> queryGoodsIdByOrder(String orderId, Integer userId);

    //查询订单状态
    public Integer queryOrderStatus(String orderId, Integer userId);

    //查询订单详情
    public Order queryOrderById(String orderId, Integer userId);

    //订单退款
    public Integer refundOrder(String orderId, Integer userId);

    //取消未支付订单
    public void cancelOrder(String orderId, Integer userId);

    //删除订单商品表对应数据
    public void cancelGoods(String orderId, Integer userId);

    //确认收货
    public Integer receiveOrder(String orderId, Integer userId);

    //确认收货后订单中物品状态改为待评论
    public Integer commentOrder(String orderId, Integer userId);

    //删除已完成订单
    public void deleteOrder(String orderId, Integer userId);

    //删除订单商品
    public void deleteOrderGoods(String orderId, Integer userId);


}
