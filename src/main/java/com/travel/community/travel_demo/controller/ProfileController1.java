package com.travel.community.travel_demo.controller;

import com.travel.community.travel_demo.dto.PaginationDTO;
import com.travel.community.travel_demo.mapper.UserMapper;
import com.travel.community.travel_demo.model.User;
import com.travel.community.travel_demo.model.UserExample;
import com.travel.community.travel_demo.service.NotificationService;
import com.travel.community.travel_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author w1586
 */
@Controller
public class ProfileController1 {
    //为什么起名ProfileController1 因为ProfileController和某配置文件重名了

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size
    )
    {

        User user1 = (User) request.getSession().getAttribute("user");

        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user1.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        User user = users.get(0);

        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if ("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
//            Long unreadCount = notificationService.unreadCount(user.getId());
//            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }

}
