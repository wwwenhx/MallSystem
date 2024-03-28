package com.drug.graduate.mapper;

import com.drug.graduate.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {
    //查询商品信息
    public List<Goods> queryGoods(Integer page, Integer limit,Integer sort, String type,String name);

    //查询商品总数
    public Integer queryGoodsCount(String type,String name);

    //获取商品详情信息
    public Goods getGoodsDetails(Integer id);

    //添加商品入购物车
    public Integer addGoods(Map<String, Object> param);

    //查询购物车是否存在该商品
    public Map<String, Object> queryShopping(Integer goods_id, Integer user_id);

    //商品存在购物车时，修改数据
    public Integer updateShopping(Map<String, Object> param);

    //获取购物车信息数据
    public List<Map<String, Object>> getShoppingData(Integer user_id);

    //根据购物车中的物品id获取物品信息
    public Goods getShoppingGoods(Integer goodsId);

    //删除购物车中的商品
    public void deleteGoods(Integer userId,Integer goodsId);

    //减少商品数量
    public Integer reduceGoods(Integer userId, Integer goodsId);

    //增加商品数量
    public Integer plusGoods(Integer uerId, Integer goodsId);

    //清空购物车
    public void clearShop(Integer userId);

    //查询下订单界面中 购物车的物品价格，数量信息
    public Map<String, Object> getSettlementShop(Integer goodsId, Integer userId);

    //查询订单中未评价的药品数据
    public List<Map<String, Object>> getEvaluateGoods(Integer userId);

    //下订单后将物品删除购物车
    public void shopOrderGoods(Integer goodsId, Integer userId);

    //添加商品评论
    public Integer addGoodsComment(Map<String, Object> param);

    //修改药品评论状态
    public Integer updateOrderGoodsStatus(Map<String, Object> param);

    //获取药品评论
    public List<Map<String, Object>> getGoodsComment(Integer goodsId);

    //获取药品详细信息
    public Map<String, Object> getGoodsDetail(Integer goodsId);

    //确认收货后增加药品销量
    public Integer addSale(Map<String ,Object> param);

    //特色专区首页药品
    public List<Map<String, Object>> specialGoods();

    //特色专区药品
    public List<Map<String, Object>> toSpecialGoods(Integer page, Integer limit, String name,Integer type);

    //特色专区药品数量
    public Integer specialGoodsCount(String name,Integer type);

    //老幼专区首页药品
    public List<Map<String, Object>> oldYoungGoods();

    //最新商品展示
    public List<Map<String, Object>> newGoods();

    //查询商品剩余库存
    public Integer goodsStock(Integer goodsId);

    //下单成功后减少库存
    public Integer updateStock(Map<String, Object> param);

}
