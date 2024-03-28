package com.drug.graduate.service;

import com.drug.graduate.entity.Address;
import com.drug.graduate.entity.User;
import com.drug.graduate.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public int userLogin(String phoneNumber, String password){
        int userId = 0;
        userId = userMapper.testAccount(phoneNumber,password);
        return userId;
    }

    public User queryAccountById(int id){
        User user = userMapper.queryAccountById(id);
        return user;
    }

    public String queryAccountByPhone(String phoneNumber){
        String testPhone = userMapper.queryAccountByPhone(phoneNumber);
        return testPhone;
    }

    public void addAccount(Map<String, Object> param){
        userMapper.addAccount(param);

    }

    public void addAddress(Address address){
        userMapper.addAddress(address);
    }

    public List<Address> queryAddressById(Integer userId){
        List<Address> address = userMapper.queryAddressById(userId);
        return address;
    }

    //删除个人收货地址
    public void deleteAddress(Integer userId, Integer id){
        userMapper.deleteAddress(userId, id);
    }

    //查询订单中的收货地址
    public Address queryOrderAddress(Integer id){ return userMapper.queryOrderAddress(id);}
}
