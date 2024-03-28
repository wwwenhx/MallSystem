package com.drug.graduate.controller;

import com.drug.graduate.entity.Address;
import com.drug.graduate.entity.Message;
import com.drug.graduate.entity.User;
import com.drug.graduate.service.MailService;
import com.drug.graduate.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/userService")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    /**
     * 登陆验证。将id，用户信息存入cookie
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Message login(HttpServletRequest request, HttpServletResponse response,String phoneNumber, String password) throws UnsupportedEncodingException {
        Message message = new Message();
        //登陆验证返回id
        int userId = userService.userLogin(phoneNumber, password);
        if(userId != 0){
            //登陆成功返回用户信息
            User user = userService.queryAccountById(userId);
            //user对象 json序列化
            JSONObject jsonUser = JSONObject.fromObject(user);
            //user存入cookie
            Cookie cookieUser = new Cookie("user", java.net.URLEncoder.encode(jsonUser.toString(), "UTF-8"));
            //userId存入cookie
            Cookie cookieUserId = new Cookie("userId",userId+"");
            cookieUser.setPath("/");
            cookieUserId.setPath("/");
            response.addCookie(cookieUser);
            response.addCookie(cookieUserId);
            message.setCode(200);
            message.setMsg("登陆成功");
            return message;
        }
        message.setCode(400);
        message.setMsg("登陆失败");
        return message;
    }

    //注册验证码
    @RequestMapping("/getRandomNumber")
    @ResponseBody
    public Message getRandomNumber(String mail){
        Message message = new Message();
        String randomNumber = "";
        for(int i = 0; i < 4; i++){
            String num = Math.round(Math.random()*9)+"";
            randomNumber += num;
        }
        String to = mail;
        String title = "药品商城验证码";
        String context = randomNumber+"";
        mailService.sendMailCode(to,title,context);
        message.setMsg("验证码发送成功");
        message.setBody(randomNumber);
        message.setCode(200);

        return message;
    }

    //用户注册
    @RequestMapping("/register")
    @ResponseBody
    public Message register(String userName, String password, String sex, String identity, String email, String phoneNumber,String realName){
        Message message = new Message();
        Map<String, Object> param = new HashMap<>();
        String testPhone = userService.queryAccountByPhone(phoneNumber);
        if(testPhone != "" && testPhone != null) {
            message.setCode(400);
            message.setMsg("手机已注册");
            return message;
        }
        param.put("name", userName);
        param.put("password", password);
        param.put("sex", sex);
        param.put("identity", identity);
        param.put("email", email);
        param.put("phone_number", phoneNumber);
        param.put("real_name",realName);
        userService.addAccount(param);

        message.setMsg("注册成功");
        message.setCode(200);
        return message;
    }

    //跳转至个人中心
    @RequestMapping("/toUserCenter")
    public String toUserCenter(){ return "user/UserCenter";}


    //查询个人收货地址
    @RequestMapping("/queryAddressById")
    @ResponseBody
    public Message queryAddressById(Integer userId){
        Message message = new Message();
        List<Address> address = userService.queryAddressById(userId);
        if(address !=  null && address.size() > 0){
            message.setBody(address);
            message.setCode(200);
            return message;
        }
        message.setMsg("暂无收货地址");
        message.setCode(400);
        return message;
    }

    //添加地址页面
    @RequestMapping("/toAddAddress")
    public String toAddAddress(){ return "user/UserAddress"; }


    //添加收货地址
    @RequestMapping("/addAddress")
    @ResponseBody
    public Message addAddress(int userId, String phoneNumber, String name, String province, String city, String county, String detail){
        Message message = new Message();
        Address address = new Address();
        address.setUserId(userId);
        address.setPhoneNumber(phoneNumber);
        address.setName(name);
        address.setProvince(province);
        address.setCity(city);
        address.setCounty(county);
        address.setDetail(detail);
        userService.addAddress(address);
        message.setMsg("添加成功");
        message.setCode(200);
        return message;
    }

    //删除个人收货地址
    @RequestMapping("/deleteAddress")
    @ResponseBody
    public Message deleteAddress(Integer userId, Integer id){
        Message message = new Message();
        userService.deleteAddress(userId, id);
        message.setCode(200);
        return message;
    }

    //跳转至结算页面
    @RequestMapping("/toSettlement")
    public String toSettlement(@CookieValue("userId") Integer userId,@CookieValue("check") String[] goodsId, @CookieValue("sum") String sum, HttpServletRequest request, Model model){
        //选中物品的id以及总价格
        model.addAttribute("check",goodsId);
        model.addAttribute("sumPrice",sum);
        //传入收货地址
        List<Address> address = userService.queryAddressById(userId);
        model.addAttribute("address", address);
        return "user/Settlement";
    }
}
