package com.travel.community.travel_demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select userName from user where userName = #{userName}")
    public String selectUserName(String userName);

    @Select("select userPassword from user where userName = #{userName}")
    public String selectUserPassword(String userName);

    @Insert("insert into user(userName, userPassword) values(#{userName}, #{userPassword})")
    public void addUser(String userName, String userPassword);

}
