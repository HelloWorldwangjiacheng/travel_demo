package com.travel.community.travel_demo.mapper;

import com.travel.community.travel_demo.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmtCreate,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{creator},#{tag})")
    void createQuestion(Question question);

    @Select("select * from question order by id desc limit #{offset},#{size} ")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(*) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} order by id desc limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") Long userId,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(*) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title=#{title},description=#{description},tag=#{tag} where id=#{id}")
    void update(Question question);
}
