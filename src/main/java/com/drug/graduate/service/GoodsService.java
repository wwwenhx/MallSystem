package com.drug.graduate.service;

import com.drug.graduate.entity.Goods;
import com.drug.graduate.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    //查询商品信息
    public List<Goods> queryGoods(Integer page, Integer limit, Integer sort, String type,String name) {
        List<Goods> goodsList = goodsMapper.queryGoods(page * limit, limit, sort, type,name);
        return goodsList;
    }

    //查询商品总数
    public int queryGoodsCount(String type,String name) {
        int count = goodsMapper.queryGoodsCount(type,name);
        return count;
    }

    //获取商品详细信息
    public Goods getGoodsDetails(Integer id) {
        Goods goods = goodsMapper.getGoodsDetails(id);
        return goods;
    }

    //首次添加商品如数据库
    public Integer addGoods(Map<String, Object> param) {
        return goodsMapper.addGoods(param);
    }

    //查询购物车中是否已存在该物品
    public Map<String, Object> queryShopping(Integer goods_id, Integer user_id) {
        return goodsMapper.queryShopping(goods_id, user_id);
    }

    //存在商品，修改商品表的数据
    public Integer updateShopping(Map<String, Object> param) {
        return goodsMapper.updateShopping(param);
    }


    //获取购物车数据
    public List<Map<String, Object>> getShoppingData(Integer user_id) {
        return goodsMapper.getShoppingData(user_id);
    }

    //根据购物车中的物品id获取物品信息
    public Goods getShoppingGoods(Integer goodsId) {
        return goodsMapper.getShoppingGoods(goodsId);
    }

    //删除购物车中的商品
    public void deleteGoods(Integer userId, Integer goodsId) {
        goodsMapper.deleteGoods(userId, goodsId);
    }

    //减少商品数量
    public Integer reduceGoods(Integer userId, Integer goodsId) {
        return goodsMapper.reduceGoods(userId, goodsId);
    }

    //增加商品数量
    public Integer plusGoods(Integer userId, Integer goodsId) {
        return goodsMapper.plusGoods(userId, goodsId);
    }

    // 清空购物车
    public void clearShop(Integer userId) {
        goodsMapper.clearShop(userId);
    }

    ////查询下订单界面中 购物车的物品价格，数量信息
    public Map<String, Object> getSettlementShop(Integer goodsId, Integer userId) {
        return goodsMapper.getSettlementShop(goodsId, userId);

    }

    //查询未评价订单中未评价的物品
    public List<Map<String, Object>> getEvaluateGoods(Integer userId) {
        return goodsMapper.getEvaluateGoods(userId);
    }

    //下单后删除购物车中订单的物品
    public void shopOrderGoods(Integer goodsId, Integer userId) {
        System.out.println(goodsId);
        System.out.println(userId);
        goodsMapper.shopOrderGoods(goodsId, userId);
    }

    //添加药品评论
    public Integer addGoodsComment(Map<String, Object> param) {
        return goodsMapper.addGoodsComment(param);
    }
    //修改药品评论状态
    public Integer updateOrderGoodsStatus(Map<String, Object> param){
        return goodsMapper.updateOrderGoodsStatus(param);
    }

    //获取药品评论
    public List<Map<String, Object>> getGoodsComment(Integer goodsId){
        return goodsMapper.getGoodsComment(goodsId);
    }

    //获取药品详细信息
    public Map<String, Object> getGoodsDetail(Integer goodsId){
        return goodsMapper.getGoodsDetail(goodsId);
    }

    //确认收货后增加药品销量
    public Integer addSale(Map<String ,Object> param){
        return goodsMapper.addSale(param);
    }

    //特色专区药品
    public List<Map<String, Object>> specialGoods(){
        return goodsMapper.specialGoods();
    }

    //特色专区药品
    public List<Map<String, Object>> toSpecialGoods(Integer page, Integer limit, String name,Integer type){
        return goodsMapper.toSpecialGoods(page*limit,limit,name,type);
    }

    //特色专区药品数量
    public Integer specialGoodsCount(String name,Integer type){
        return goodsMapper.specialGoodsCount(name,type);
    }

    //老幼专区首页药品
    public List<Map<String, Object>> oldYoungGoods(){ return goodsMapper.oldYoungGoods();}

    //最新商品展示
    public List<Map<String, Object>> newGoods(){ return goodsMapper.newGoods(); }

    //查询商品剩余库存
    public Integer goodsStock(Integer goodsId){
        return goodsMapper.goodsStock(goodsId);
    }

    //下单成功后减少库存
    public Integer updateStock(Map<String, Object> param){
        return goodsMapper.updateStock(param);
    }
}
