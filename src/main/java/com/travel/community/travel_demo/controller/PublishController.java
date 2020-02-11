package com.travel.community.travel_demo.controller;

import com.travel.community.travel_demo.cache.TagCache;
import com.travel.community.travel_demo.dto.QuestionDTO;
import com.travel.community.travel_demo.mapper.QuestionMapper;
import com.travel.community.travel_demo.mapper.UserMapper;
import com.travel.community.travel_demo.model.Question;
import com.travel.community.travel_demo.model.User;
import com.travel.community.travel_demo.model.UserExample;
import com.travel.community.travel_demo.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @ApiOperation("发布接口/publish")
    @GetMapping("/publish")
    public String Publish(Model model){
//        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "tag") String tag,
            HttpServletRequest request,
            Model model,
            @RequestParam(value = "id") Long id)
    {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
//        model.addAttribute("tags", TagCache.get());

        //因为本项目是专注于后端的，所以把这些校验放到后端，其实正常的项目开发中应该是放到前端js来校验的，再保险一点后端也要验证因为某些手段可以绕过前端验证
        if (title == null || title == "" ){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        if (description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }

        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
//        对标签合法性的检测
//        String invaild = TagCache.filterInvaild(tag);
//        if (StringUtils.isNoneBlank(invaild)){
//            model.addAttribute("error","输入非法标签"+invaild);
//            return "publish";
//        }


        User user1 = (User) request.getSession().getAttribute("user");

        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user1.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        User user = users.get(0);

        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        questionService.createOrUpdate(question);
//        questionMapper.createQuestion(question);
        return "redirect:/";
    }


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);

        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
//        model.addAttribute("tags", TagCache.get());

        return "publish";
    }
}
