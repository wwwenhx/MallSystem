package com.drug.graduate.controller;

import com.drug.graduate.entity.Message;
import com.drug.graduate.entity.Order;
import com.drug.graduate.service.GoodsService;
import com.drug.graduate.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/goodsService")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    //默认药品
    @RequestMapping("/toGoods")
    public String toGoods(@RequestParam(name = "type",required = false,defaultValue = "")String type, Model model){
        model.addAttribute("type",type);
        return "index/toGoods";
    }

    //打开药品评论页面
    @RequestMapping("toGoodsComment")
    public String toGoodsComment(@CookieValue("userId") Integer userId, Integer goodsId,String orderId,Model model){
        model.addAttribute("userId",userId);
        model.addAttribute("goodsId",goodsId);
        model.addAttribute("orderId",orderId);
        return "user/GoodsComment";
    }

    //添加药品评论
    @RequestMapping("/addGoodsComment")
    @ResponseBody
    public Message addGoodsComment(Integer userId, Integer goodsId, String comment,String orderId){
        Message message = new Message();
        Map<String, Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("goodsId",goodsId);
        param.put("comment",comment);
        param.put("orderId",orderId);
        goodsService.addGoodsComment(param);
        //修改订单商品表中的物品评论状态
        goodsService.updateOrderGoodsStatus(param);
        message.setCode(200);
        return message;
    }

    //获取药品评论
    @RequestMapping("/getGoodsComment")
    @ResponseBody
    public Message getGoodsComment(Integer goodsId){
        Message message = new Message();
        List<Map<String, Object>> commentList = goodsService.getGoodsComment(goodsId);
        if(commentList != null && commentList.size()>0){
            message.setBody(commentList);
            message.setCode(200);
            return message;
        }
        message.setMsg("暂无评论");
        message.setCode(400);
        return message;
    }

    //获取特色药品数据
    @RequestMapping("/specialGoods")
    @ResponseBody
    public Message specialGoods(){
        Message message = new Message();
        List<Map<String, Object>> list = goodsService.specialGoods();
        if (list != null){
            message.setCode(200);
            message.setBody(list);
            return message;
        }
        message.setBody("暂无数据");
        message.setCode(400);
        return message;
    }

    //获取老幼药品数据
    @RequestMapping("/oldYoungGoods")
    @ResponseBody
    public Message oldYoungGoods(){
        Message message = new Message();
        List<Map<String, Object>> list = goodsService.oldYoungGoods();
        if (list != null){
            message.setCode(200);
            message.setBody(list);
            return message;
        }
        message.setBody("暂无数据");
        message.setCode(400);
        return message;
    }

    //获取最新药品数据
    @RequestMapping("/newGoods")
    @ResponseBody
    public Message newGoods(){
        Message message = new Message();
        List<Map<String, Object>> list = goodsService.newGoods();
        if (list != null){
            message.setCode(200);
            message.setBody(list);
            return message;
        }
        message.setBody("暂无数据");
        message.setCode(400);
        return message;
    }


    //获取药品详细数据
    @RequestMapping("/getGoodsDetail")
    @ResponseBody
    public Map<String, Object> getGoodsDetail(Integer goodsId){
        Map<String, Object> param = new HashMap<>();
        param = goodsService.getGoodsDetail(goodsId);
        return param;
    }

    //获取评论药品时的订单信息
    @RequestMapping("/getGoodsOrder")
    @ResponseBody
    public Order getGoodsOrder(String orderId, Integer userId){
        return orderService.queryOrderById(orderId, userId);
    }

}
