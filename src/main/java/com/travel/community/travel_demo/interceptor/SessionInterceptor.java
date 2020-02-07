package com.travel.community.travel_demo.interceptor;

import com.travel.community.travel_demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor {
    @Autowired
    private UserMapper userMapper;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length != 0){
//            for(Cookie cookie : cookies ) {
//                if (cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    UserExample userExample = new UserExample();
//                    userExample.createCriteria()
//                            .andTokenEqualTo(token);
//
//                    List<User> users = userMapper.selectByExample(userExample);
////                    User user = userMapper.findByToken(token);
////                    因为返回的是列表类型，所以从判断user是否为空，改为判断列表长度是否为0
//                    if(users.size() != 0){
//                        request.getSession().setAttribute("user",users.get(0));
//                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
//                        request.getSession().setAttribute("unreadCount",unreadCount);
//                    }
//                    break;
//                }
//            }
//        }
//        return true;
//    }
}
