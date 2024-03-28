package com.drug.graduate.mapper;
import com.drug.graduate.entity.Address;
import com.drug.graduate.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 注册账号、插入数据库
     * @param param
     * @return
     */
    public void addAccount(Map<String, Object> param);

    /**
     * 根据用户输入手机号查询是否注册
     */
    public String queryAccountByPhone(String phoneNumber);

    /**
     *登陆验证查询，返回用户id
     */
    public Integer testAccount(String phoneNumber, String password);

    /**
     * 根据id查询账号信息
     * @param id
     * @return
     */
    public User queryAccountById(int id);

    /**
     * 根据id注销账号删除数据库
     * @param id
     */
    public void deleteAccountById(int id);

    /**
     * 添加收货地址
     */

    public Integer addAddress(Address address);

    /**
     * 删除个人收货地址
     */
    public void deleteAddress(Integer userId, Integer id);

    /**
     * 查询个人收货地址
     */
    public List<Address> queryAddressById(Integer userId);

    /**
     * 根据订单提供的收货地址ID
     * 查询地址
     */
    public Address queryOrderAddress(Integer id);
}
