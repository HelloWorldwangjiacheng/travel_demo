package com.travel.community.travel_demo.controller;

import com.travel.community.travel_demo.dto.CommentCreateDTO;
import com.travel.community.travel_demo.dto.CommentDTO;
import com.travel.community.travel_demo.dto.ResultDTO;
import com.travel.community.travel_demo.enums.CommentTypeEnum;
import com.travel.community.travel_demo.exception.CustomizeErrorCode;
import com.travel.community.travel_demo.mapper.CommentMapper;
import com.travel.community.travel_demo.mapper.UserMapper;
import com.travel.community.travel_demo.model.Comment;
import com.travel.community.travel_demo.model.User;
import com.travel.community.travel_demo.model.UserExample;
import com.travel.community.travel_demo.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
public class CommentController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentService commentService;

    //用requestBody接收json格式的数据
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user1 = (User) request.getSession().getAttribute("user");
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user1.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        User user = users.get(0);

        if (user == null) {
            //向内封装，封装到CustomizeErrorCode这个枚举类类中
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
//        commentCreateDTO == null || commentCreateDTO.getContent() == null || commentCreateDTO.getContent() == ""
        //在org.apache.commons：commons-lang3下面的StringUntils方法类的使用效果等同于commentCreateDTO.getContent() == null || commentCreateDTO.getContent() == ""
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
//        commentMapper.insert(comment);
        commentService.insert(comment, user);

        Map<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("message", "成功");
        return ResultDTO.okOf();
    }




    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {

        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);

        return ResultDTO.okOf(commentDTOS);
    }



}
