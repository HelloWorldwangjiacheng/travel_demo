package com.travel.community.travel_demo.service;

import com.travel.community.travel_demo.dto.PaginationDTO;
import com.travel.community.travel_demo.dto.QuestionDTO;
import com.travel.community.travel_demo.exception.CustomizeErrorCode;
import com.travel.community.travel_demo.exception.CustomizeException;
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

    /**
     *   这是在首页的分页展示
     */
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
            User user  = userMapper.findById(question.getCreator());

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }

    /**
     * 这是在我的问题中的分页
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        //totalCount是数据库question表中的记录的数量
        Integer totalCount = questionMapper.countByUserId(userId);
//        QuestionExample questionExample = new QuestionExample();
//        questionExample.createCriteria()
//                .andCreatorEqualTo(userId);
//        Integer totalCount = (int) questionMapper.countByExample(questionExample);

        if (totalCount%size == 0){
            totalPage = totalCount/size;
        } else {
            totalPage = totalCount/size +1;
        }

        if (page<1){ page=1; }
        if (page>totalPage){ page = totalPage; }
        paginationDTO.setPagination(totalPage,page);
//        size*(i-1)
        Integer offset = size*(page-1);
        if (offset<1) offset=1;

        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
//        QuestionExample example = new QuestionExample();
//        example.createCriteria()
//                .andCreatorEqualTo(userId);
//        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions){
//            User user = userMapper.selectByPrimaryKey(question.getCreator());
            User user  = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//将question上的所有属性复制到questionDTO上去
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
//        Question question = questionMapper.selectByPrimaryKey(id);
        Question question = questionMapper.getById(id);
        //自定义异常抛出
        if (question == null){
            //当你查找的问题不存在时，比如你找id为9999999999的问题，显然是不存在的，就会抛出这个异常
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);

//        User user = userMapper.selectByPrimaryKey(question.getCreator());
        User user  = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
//            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
//            questionMapper.create(question);
            //创建实质就是插入数据库
            questionMapper.createQuestion(question);
        }else {
            //更新
            questionMapper.update(question);
//            question.setGmtModified(question.getGmtCreate());
            /*
            Question updateQuestion = new Question();
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example  = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion,example);
            if (updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

             */


        }
    }
}
