package com.travel.community.travel_demo.controller;

import com.travel.community.travel_demo.dto.CommentDTO;
import com.travel.community.travel_demo.dto.QuestionDTO;
import com.travel.community.travel_demo.enums.CommentTypeEnum;
import com.travel.community.travel_demo.service.CommentService;
import com.travel.community.travel_demo.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@ApiOperation("问题控制类QuestionController")
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @ApiOperation("得到某个确切id的question")
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model)
    {
        //通过questionService去调用questionMapper
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
