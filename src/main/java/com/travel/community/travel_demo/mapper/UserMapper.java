package com.travel.community.travel_demo.mapper;

import com.travel.community.travel_demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Select("select userName from user where accountId = #{accountId}")
    public String selectUserName(String accountId);

    @Select("select accountId from user where accountId = #{accountId}")
    public String selectAccountId(String accountId);

    @Select("select userPassword from user where accountId = #{accountId}")
    public String selectUserPassword(String accountId);

    @Insert("insert into user(userName, accountId,userPassword,token,gmtCreate) values(#{userName},#{accountId},#{userPassword},#{token},#{gmtCreate})")
    public void addUser(User user);

    @Insert("insert into user(userName, accountId,userPassword,token,gmtCreate) values(#{userName},#{accountId},#{accountId},#{token},#{gmtCreate})")
    public void githubInsert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select token from user where accountId = #{accountId}")
    String selectUserToken(String accountId);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);
}
