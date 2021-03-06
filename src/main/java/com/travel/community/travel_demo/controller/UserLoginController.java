package com.travel.community.travel_demo.controller;

import com.travel.community.travel_demo.mapper.UserMapper;
import com.travel.community.travel_demo.model.User;
import com.travel.community.travel_demo.model.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

/**
 * @author w1586
 */
@Controller
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/telephoneRegister")
    public String telephoneRegister() {
        return "telephoneRegister";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    @RequestMapping("/successLogin")
//    public String successLogin(){
//        return "successLogin";
//    }


    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response)
    {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

//    @ResponseBody
//    @RequestMapping(value = "/successLogin", method = RequestMethod.POST)
    @PostMapping(value = "/successLogin")
    public String successLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(name = "userName") String userName)
    {
        if(userName != null){
            User user = new User();
//            String token = UUID.randomUUID().toString();
//            String token = userMapper.selectUserToken(userName);
            UserExample example = new UserExample();
            example.createCriteria().andAccountIdEqualTo(userName);
            List<User> users = userMapper.selectByExample(example);
            String token = users.get(0).getToken();
            user.setToken(token);
//            user.setUserName(userMapper.selectUserName(userName));
            user.setUserName(users.get(0).getUserName());
            user.setAccountId(userName);

//            userMapper.githubInsert(user);
            Cookie cookie = new Cookie("token",token);
            response.addCookie(cookie);

            // 登录成功，写cookie和session
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            //登录失败，重新登录
            return "redirect:/";
        }
//        return "successLogin";
    }

//    @RequestMapping("/successRegister")
    @GetMapping("/successRegister")
    public String successRegister() { return "successRegister"; }

    @ResponseBody
//    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @PostMapping(value = "/select")
    public String select(@RequestBody User user) {
        System.out.println(user.getUserName()+"---"+user.getAccountId());
//        String result = userMapper.selectUserName(user.getAccountId());
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        String result = users.get(0).getUserName();
        System.out.println(result);
        if (result == null) {
            return "0";
        }
        return "1";
    }

    @ResponseBody
//    @RequestMapping(value = "/selectUserName", method = RequestMethod.POST)
    @PostMapping(value = "/selectUserName")
    public String selectUserName(@RequestBody User user,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        String userName = user.getUserName();
        String accountId = userName;
        String userPassword = user.getUserPassword();
        System.out.println(accountId+"---"+userName+"---"+userPassword);

        String result = "-1";

        //将输入的密码加密
        String passwordMD5 = passwordMD5(accountId, userPassword);

        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(example);

        //用户不存在
//        userMapper.selectAccountId(accountId) == null
        if (users.get(0).getAccountId() == null) {
//            return "用户不存在";
            result = "0";
            System.out.println(accountId+"---"+userName+"---"+userPassword);
            return result;
            //用户存在，但密码输入错误
//            !userMapper.selectUserPassword(accountId).equals(passwordMD5)
        }else if(!users.get(0).getUserPassword().equals(passwordMD5) ){
            result = "1";
            return result;
//            return "账号或密码输入错误";
//            userMapper.selectUserPassword(accountId).equals(passwordMD5)
        }else if(users.get(0).getUserPassword().equals(passwordMD5)) {
            try{
                result = "2";
                System.out.println(result);

                User user1 = new User();
//                String token = userMapper.selectUserToken(accountId);
                String token = users.get(0).getToken();
                user1.setToken(token);
//                user1.setUserName(userMapper.selectUserName(accountId));
                user1.setUserName(users.get(0).getUserName());
                user1.setAccountId(accountId);
//            user.setAvatarUrl(githubUser.getAvatarUrl());

                Cookie cookie = new Cookie("token",token);
                response.addCookie(cookie);

                // 登录成功，写cookie和session
                request.getSession().setAttribute("user",user1);

//            return "成功登录";
                return result;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return result;
    }

    @ResponseBody
//    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @PostMapping(value = "/addUser")
    public String addUser(@RequestBody User user) {
        String userName = user.getUserName();
        String accountId = user.getAccountId();
        String userPassword = user.getUserPassword();
        System.out.println(accountId+"--"+userName+"--"+userPassword);
        String passwordMD5 = passwordMD5(accountId, userPassword);
        String token = UUID.randomUUID().toString();
        User user1 = new User();

        user1.setUserName(user.getUserName());
        user1.setAccountId(user.getAccountId());
        user1.setUserPassword(passwordMD5);
        user1.setToken(token);
        user1.setGmtCreate(System.currentTimeMillis());

//        userMapper.addUser(user1);
        userMapper.insert(user1);
        return "1";
    }

    /**
     *     对密码进行MD5加密
     */
    public String passwordMD5(String userName, String userPassword) {
        // 需要加密的字符串
        String src = userName + userPassword;
        try {
            // 加密对象，指定加密方式
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 准备要加密的数据
            byte[] b = src.getBytes();
            // 加密：MD5加密一种被广泛使用的密码散列函数，
            // 可以产生出一个128位（16字节）的散列值（hash value），用于确保信息传输完整一致
            byte[] digest = md5.digest(b);
            // 十六进制的字符
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5',
                    '6', '7', 'A', 'B', 'C', 'd', 'o', '*', '#', '/'};
            StringBuffer sb = new StringBuffer();
            // 处理成十六进制的字符串(通常)
            // 遍历加密后的密码，将每个元素向右位移4位，然后与15进行与运算(byte变成数字)
            for (byte bb : digest) {
                sb.append(chars[(bb >> 4) & 15]);
                sb.append(chars[bb & 15]);
            }
            // 打印加密后的字符串
            System.out.println(sb);
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
