package com.travel.community.travel_demo.mapper;

import com.travel.community.travel_demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmtCreate,createor,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{createor},#{tag})")
    public void createQuestion(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(*) from question")
    Integer count();
}
