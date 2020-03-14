package com.travel.community.travel_demo.controller;

import com.travel.community.travel_demo.dto.PaginationDTO;
import com.travel.community.travel_demo.dto.QuestionDTO;
import com.travel.community.travel_demo.mapper.UserMapper;
import com.travel.community.travel_demo.model.Question;
import com.travel.community.travel_demo.model.User;
import com.travel.community.travel_demo.model.UserExample;
import com.travel.community.travel_demo.service.NotificationService;
import com.travel.community.travel_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author w1586
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "2") Integer size
            )
    {
//
//        User user1 = (User) request.getSession().getAttribute("user");
//
//        UserExample example = new UserExample();
//        example.createCriteria().andAccountIdEqualTo(user1.getAccountId());
//        List<User> users = userMapper.selectByExample(example);
//        User user = users.get(0);

        PaginationDTO<QuestionDTO> paginationDTO = questionService.list(page,size);

//        Long unreadCount = notificationService.unreadCount(user.getId());
//        model.addAttribute("unreadCount",unreadCount);
        model.addAttribute("pagination",paginationDTO);
        return "index";
    }

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
}
