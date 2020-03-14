package com.travel.community.travel_demo.controller;

import com.travel.community.travel_demo.dto.NotificationDTO;
import com.travel.community.travel_demo.enums.NotificationTypeEnum;
import com.travel.community.travel_demo.mapper.UserMapper;
import com.travel.community.travel_demo.model.User;
import com.travel.community.travel_demo.model.UserExample;
import com.travel.community.travel_demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable(name = "id") Long id,
                               HttpServletRequest request)
    {
        User user1 = (User) request.getSession().getAttribute("user");
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user1.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        User user = users.get(0);

        if (user == null)   return "redirect:/";

        NotificationDTO notificationDTO = notificationService.read(id,user);
        if (notificationDTO.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }


    }


}
