package com.drug.graduate.controller;

import com.drug.graduate.entity.Goods;
import com.drug.graduate.entity.Message;
import com.drug.graduate.service.GoodsService;
import com.drug.graduate.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/indexService")
public class IndexController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    IndexService indexService;

    @RequestMapping("/index")
    public String toIndex(HttpServletRequest request, HttpServletResponse response, Model model,String order) {
        return "index/Index";
    }

    //轮播图url
    @RequestMapping("/rotationChart")
    @ResponseBody
    public List<Map<String, Object>> rotationChart(){
        List<Map<String, Object>> imgList = indexService.rotationImg();
        return imgList;
    }

    //人气药品展示
    @RequestMapping("/popularityGoods")
    @ResponseBody
    public List<Map<String, Object>> popularityGoods(){
        List<Map<String, Object>> goodsList = indexService.popularityGoods();
        return goodsList;
    }

    //最新药品展示
    @RequestMapping("/newGoods")
    @ResponseBody
    public List<Map<String, Object>> newGoods(){
        List<Map<String, Object>> goodsList = goodsService.newGoods();
        return goodsList;
    }

    //特色药品首页界面
    @RequestMapping("/specialGoods")
    public String specialGoods(){ return "index/specialGoods";}

    //特色药品界面
    @RequestMapping("/toSpecialGoods")
    public String toSpecialGoods(Integer type, Model model){
        model.addAttribute("type",type);
        return "index/toSpecialGoods";
    }

    //特色药品数据
    @RequestMapping("getSpecialGoods")
    @ResponseBody
    public Map<String, Object> getSpecialGoods(Integer page, Integer limit,Integer type,
                                   @RequestParam(name = "name",required = false,defaultValue = "")String name){
        Message message = new Message();
        List<Map<String, Object>> list = goodsService.toSpecialGoods(page-1,limit,name,type);
        int count = goodsService.specialGoodsCount(name,type);
        Map<String, Object> param = new HashMap<>();
        param.put("data",list);
        param.put("count",count);
        return param;
    }

    //老幼药品首页展示
    @RequestMapping("/oldYoungGoods")
    public String oldYoungGoods(){ return "index/oldYoungGoods";}




    //用户账号登陆注册界面
    @RequestMapping("/account")
    public String toLogin(HttpServletRequest request){
        return "user/UserAccount";
    }


    //跳转至药品界面
    @RequestMapping("/goods")
    public String toGoods(){return "index/goods";}

    //获取药品数据
    @RequestMapping("/getGoods")
    @ResponseBody
    public Map<String, Object> queryGoods(Integer page, Integer limit,
                                          @RequestParam(name = "sort",required = false,defaultValue = "")Integer sort,
                                          @RequestParam(name = "type",required = false,defaultValue = "")String type,
                                          @RequestParam(name = "name",required = false,defaultValue = "")String name){
        List<Goods> goodsList = goodsService.queryGoods(page-1, limit,sort,type,name);
        int count = goodsService.queryGoodsCount(type,name);
        Map<String, Object> map = new HashMap<>();
        map.put("data", goodsList);
        map.put("count", count);
        return map;
    }

    //药品详情页
    @RequestMapping("/goodsDetail")
    public String goodsDetail(Model model, @RequestParam("id") Integer id){
        model.addAttribute("id",id);
        return "index/goodsDetail";
    }

    //药品详情页信息
    @RequestMapping("/getGoodsDetails")
    @ResponseBody
    public Message getGoodsDetails(Integer id){
        Message message = new Message();
        Goods goodsDetail = goodsService.getGoodsDetails(id);
        if(goodsDetail != null){
            message.setCode(200);
            message.setBody(goodsDetail);
            message.setMsg("操作成功");
            return message;
        }
        message.setCode(400);
        message.setMsg("操作失败");
        return message;
    }

    //添加商品进入数据库
    @RequestMapping("addGoods")
    @ResponseBody
    public Message addGoods(Integer id, Integer userId){
        Message message = new Message();
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> param2 = new HashMap<>();
        //获取商品库存
        Integer stock = goodsService.goodsStock(id);
        if(stock<=0){
            message.setCode(400);
            message.setMsg("库存不足");
            return message;
        }
        //获取商品信息
        Goods goods = goodsService.getGoodsDetails(id);
        double price = goods.getPrice();
        //查询购物车中是否已经存在该物品
        param2 = goodsService.queryShopping(id, userId);

        //如果存在，则修改商品表的数据
        if(param2 != null){
            int num = (int) param2.get("num");
            double price2 = (double) param2.get("price");
            num += 1;
            if (num > stock){
                message.setCode(400);
                message.setMsg("库存不足");
                return message;
            }
            param.put("goodsId", id);
            param.put("userId", userId);
            param.put("price", price2);
            param.put("num", num);
            param.put("sumPrice", price2*num);
            goodsService.updateShopping(param);
            message.setMsg("已存在于购物车，添加成功");
            return message;
        }

        //如果不存在，直接插入购物车表
        param.put("goodsId", id);
        param.put("userId", userId);
        param.put("price", price);
        param.put("sumPrice",price);
        goodsService.addGoods(param);
        message.setMsg("添加购物车成功");
        return message;
    }

    //打开购物车页面
    @RequestMapping("/shop")
    public String shop(){ return "index/shop"; }

    //获取购物车数据
    @RequestMapping("/getShopping")
    @ResponseBody
    public Map<String, Object> getShopping(Integer goodsId, Integer userId){
        Map<String, Object> param = new HashMap<>();
        //根据id查询该用户的购物车物品列表
        List<Map<String, Object>> list = goodsService.getShoppingData(userId);
        List<Goods> goodsList = new ArrayList<>();

        //根据获取的购物车物品列表，获取物品详细信息
        for (int i = 0; i < list.size(); i++){
            int goods_id = (int) list.get(i).get("goodsId");
            Goods goods = goodsService.getShoppingGoods(goods_id);
            goodsList.add(goods);
        }

        param.put("shoppingData", list);
        param.put("goodsData", goodsList);
        return param;
    }

    //删除购物车商品
    @RequestMapping("/deleteGoods")
    @ResponseBody
    public Message deleteGoods(Integer userId,Integer goodsId){
        Message message = new Message();
        goodsService.deleteGoods(userId, goodsId);
        message.setMsg("删除该商品成功");
        return message;
    }

    //减少商品数量
    @RequestMapping("/reduceGoods")
    @ResponseBody
    public Message reduceGoods(Integer userId, Integer goodsId){
        Message message = new Message();
        goodsService.reduceGoods(userId, goodsId);
        message.setCode(200);
        message.setMsg("操作成功");
        return message;
    }

    //增加商品数量
    @RequestMapping("/plusGoods")
    @ResponseBody
    public Message plusGoods(Integer userId, Integer goodsId,Integer num){
        Message message = new Message();
        //商品库存量
        int stock = goodsService.goodsStock(goodsId);
        if (stock < num + 1){
            message.setCode(400);
            message.setMsg("库存不足无法增加");
            return message;
        }
        goodsService.plusGoods(userId, goodsId);
        message.setCode(200);
        message.setMsg("操作成功");
        return message;
    }

    //清空购物车
    @RequestMapping("/clearShop")
    @ResponseBody
    public Message clearShop(Integer userId){
        Message message = new Message();
        goodsService.clearShop(userId);
        message.setCode(200);
        message.setMsg("操作成功");
        return message;
    }

    //获取订单中物品的数据
    @RequestMapping("/getSettlementGoods")
    @ResponseBody
    public Map<String, Object> getSettlementGoods(Integer goodsId,Integer userId){
        Map<String, Object> param = goodsService.getSettlementShop(goodsId,userId);
        Goods goods = goodsService.getShoppingGoods(goodsId);
        Map<String, Object> param2 = new HashMap<>();
        param2.put("goods", goods);
        param2.put("settlementGoods", param);
        return param2;
    }


}
