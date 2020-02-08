package com.travel.community.travel_demo.service;

import com.travel.community.travel_demo.dto.PaginationDTO;
import com.travel.community.travel_demo.dto.QuestionDTO;
import com.travel.community.travel_demo.mapper.QuestionMapper;
import com.travel.community.travel_demo.mapper.UserMapper;
import com.travel.community.travel_demo.model.Question;
import com.travel.community.travel_demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    public PaginationDTO<QuestionDTO> list(Integer page, Integer size){


        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        //offset偏移
        Integer offset = size * (page - 1);

        Integer totalPage;
        //总记录数
        Integer totalCount = questionMapper.count();

        if (totalCount % size == 0){
            totalPage = totalCount/size;
        } else {
            totalPage = totalCount/size +1;
        }
        if (page<1){ page=1; }
        if (page>totalPage){ page = totalPage; }
        paginationDTO.setPagination(totalPage,page);

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();



        for (Question question : questions){
            User user  = userMapper.findById(question.getCreateor());

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setData(questionDTOS);



        return paginationDTO;
    }

}
